package org.wyyt.db2es.client.http.handler;

import cn.hutool.core.util.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.TopicPartition;
import org.wyyt.db2es.client.common.CheckpointExt;
import org.wyyt.db2es.client.core.RecordListenerImpl;
import org.wyyt.db2es.client.entity.Processor;
import org.wyyt.db2es.client.http.BaseHandler;
import org.wyyt.db2es.client.http.Param;
import org.wyyt.db2es.client.http.anno.PostMapping;
import org.wyyt.db2es.client.http.anno.RestController;
import org.wyyt.db2es.core.entity.domain.TopicOffset;
import org.wyyt.db2es.core.entity.persistent.Topic;
import org.wyyt.db2es.core.entity.view.TopicVo;
import org.wyyt.db2es.core.util.CommonUtils;
import org.wyyt.tool.exception.ExceptionTool;
import org.wyyt.tool.web.Result;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * rest controller of topic
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@RestController("topic")
public final class TopicHandler extends BaseHandler {
    @PostMapping("list")
    public final Result<List<TopicVo>> list(final Param param) throws Exception {
        final String searchTopicName = param.getPostString("topicName");

        final Set<String> topicNames = this.context.getConfig().getTopicMap().keySet();
        final List<TopicPartition> topicPartitionList = new ArrayList<>(topicNames.size());

        for (final String topicName : topicNames) {
            if (StringUtils.isEmpty(searchTopicName) || topicName.contains(searchTopicName)) {
                topicPartitionList.add(new TopicPartition(topicName, 0));
            }
        }

        final Map<TopicPartition, TopicOffset> offsetMap = this.context.getKafkaAdminClientWrapper().listOffset(topicPartitionList);

        final List<TopicVo> result = new ArrayList<>(topicNames.size());
        for (final Map.Entry<TopicPartition, TopicOffset> pair : offsetMap.entrySet()) {
            final TopicVo topicVo = new TopicVo();
            topicVo.setTopicName(pair.getKey().topic());
            topicVo.setTopicOffset(pair.getValue());

            final Processor processor = this.context.getProcessorWrapper().getByTopicPartition(pair.getKey());
            if (null != processor) {
                topicVo.setIsActive(true);
                topicVo.setTps(processor.getTps());
                topicVo.setErrorMsg(ExceptionTool.getRootCauseMessage(processor.getException()));
            } else {
                topicVo.setTps(0);
                topicVo.setIsActive(false);
            }
            topicVo.setVersion(CommonUtils.getVersion());
            result.add(topicVo);
        }
        result.sort(Comparator.comparing(TopicVo::getTopicName));
        return Result.success(result);
    }

    @PostMapping("startTopic")
    public synchronized final Result<?> startTopic(final Param param) throws Exception {
        final String topicName = param.getPostString("topicName"); //required
        final String partition = param.getPostString("partition"); //required
        final String offset = param.getPostString("offset");
        final String timestamp = param.getPostString("timestamp");

        if (StringUtils.isEmpty(topicName)) {
            return Result.error("parameter[topicName] is required");
        }
        if (StringUtils.isEmpty(partition)) {
            return Result.error("parameter[partition] is required");
        }

        final TopicPartition topicPartition = new TopicPartition(topicName, Integer.parseInt(partition));

        if (this.context.getProcessorWrapper().containsTopic(topicPartition)) {
            return Result.error(String.format("Topic[%s] already installed", topicPartition));
        }

        CheckpointExt checkpoint = new CheckpointExt(
                topicPartition,
                StringUtils.isEmpty(offset) ? -1L : Long.parseLong(offset),
                StringUtils.isEmpty(timestamp) ? -1L : Long.parseLong(timestamp)
        );

        if (checkpoint.getOffset() < 0 && checkpoint.getTimestamp() < 0) {
            checkpoint = null;
        }

        this.context.getProcessorWrapper().startTopic(topicPartition, checkpoint, new RecordListenerImpl(this.context));
        return Result.success();
    }

    @PostMapping("stopTopic")
    public synchronized final Result<?> stopTopic(final Param param) throws InterruptedException {
        final String topicName = param.getPostString("topicName"); //required
        final String partition = param.getPostString("partition"); //required
        if (StringUtils.isEmpty(topicName)) {
            return Result.error("parameter[topicName] is required");
        }
        final TopicPartition topicPartition = new TopicPartition(topicName, Integer.parseInt(partition));
        if (this.context.getProcessorWrapper().containsTopic(topicPartition)) {
            this.context.getProcessorWrapper().stopTopic(topicPartition);
        }
        return Result.success();
    }

    @PostMapping("restartTopic")
    public synchronized final Result<?> restartTopic(final Param param) throws Exception {
        final String topicName = param.getPostString("topicName"); //required
        final String partition = param.getPostString("partition"); //required
        final String offset = param.getPostString("offset");
        final String timestamp = param.getPostString("timestamp");

        if (StringUtils.isEmpty(topicName)) {
            return Result.error("parameter[topicName] is required.");
        }
        if (StringUtils.isEmpty(partition)) {
            return Result.error("parameter[partition] is required");
        }
        if (StringUtils.isEmpty(offset) && StringUtils.isEmpty(timestamp)) {
            return Result.error("parameter[offset] and [timestamp] need at least one.");
        }

        TopicPartition topicPartition = new TopicPartition(topicName, Integer.parseInt(partition));
        if (!StringUtils.isEmpty(timestamp)) {
            checkTimestamp(topicPartition, Long.parseLong(timestamp));
        }
        if (this.context.getProcessorWrapper().containsTopic(topicPartition)) {
            stopTopic(param);
        }
        this.startTopic(param);
        return Result.success();
    }

    @PostMapping("startAll")
    public synchronized final Result<?> startAll(final Param param) throws Exception {
        final String offset = param.getPostString("offset");
        final String timestamp = param.getPostString("timestamp");

        CheckpointExt checkpoint = new CheckpointExt(
                null,
                StringUtils.isEmpty(offset) ? -1L : Long.parseLong(offset),
                StringUtils.isEmpty(timestamp) ? -1L : Long.parseLong(timestamp)
        );

        if (checkpoint.getOffset() < 0 && checkpoint.getTimestamp() < 0) {
            checkpoint = null;
        }

        this.context.getProcessorWrapper().startAll(checkpoint);
        return Result.success();
    }

    @PostMapping("stopAll")
    public synchronized final Result<?> stopAll(final Param param) throws InterruptedException {
        this.context.getProcessorWrapper().stopAll();
        return Result.success();
    }

    @PostMapping("calcOffsetByTimestamp")
    public final Result<TopicOffset> calcOffsetByTimestamp(final Param param) throws InterruptedException, ExecutionException {
        final String topicName = param.getPostString("topicName"); //required
        final String partition = param.getPostString("partition"); //required
        final String timestamp = param.getPostString("timestamp"); //required

        if (StringUtils.isEmpty(topicName)) {
            return Result.error("parameter[topicName] is required.");
        }
        if (StringUtils.isEmpty(timestamp)) {
            return Result.error("parameter[timestamp] is required");
        }
        if (!NumberUtil.isLong(timestamp)) {
            return Result.error("parameter[timestamp] is must be timestamp format");
        }

        final TopicPartition topicPartition = new TopicPartition(topicName, Integer.parseInt(partition));
        return Result.success(this.context.getKafkaAdminClientWrapper().listOffsetForTimes(topicPartition, Long.parseLong(timestamp)));
    }

    @PostMapping("installTopic")
    public synchronized final Result<Boolean> installTopic(final Param param) throws Exception {
        final String topicId = param.getPostString("topicId"); //required
        if (StringUtils.isEmpty(topicId)) {
            return Result.error("parameter[topicId] is required");
        }

        final Topic topic = this.context.getDbWrapper().getTopicById(Long.parseLong(topicId));
        if (null == topic) {
            return Result.error(String.format("不存在id=%s的主题", topicId));
        }
        final TopicPartition topicPartition = new TopicPartition(topic.getName(), 0);
        final Processor processor = this.context.getProcessorWrapper().getByTopicPartition(topicPartition);
        if (null != processor) {
            return Result.error(String.format("主题[%s]正在运行中, 无法安装", topicPartition));
        }
        if (this.context.getConfig().getTopicMap().containsKey(topic.getName())) {
            return Result.error(String.format("主题[%s]已存在db2es_id = %s中", topicPartition, this.context.getConfig().getDb2EsId()));
        }
        this.context.getConfig().getTopicMap().put(topic.getName(), topic);
        this.context.getProcessorWrapper().startTopic(topicPartition, null, new RecordListenerImpl(this.context));
        return Result.success(true);
    }

    @PostMapping("uninstallTopic")
    public synchronized final Result<Boolean> uninstallTopic(final Param param) throws InterruptedException {
        final String topicName = param.getPostString("topicName"); //required
        if (StringUtils.isEmpty(topicName)) {
            return Result.error("parameter[topicName] is required");
        }
        final TopicPartition topicPartition = new TopicPartition(topicName, 0);
        final Processor processor = this.context.getProcessorWrapper().getByTopicPartition(topicPartition);
        if (null != processor) {
            return Result.error(String.format("主题[%s]正在运行中, 请先暂停后在卸载", topicPartition));
        }
        final Topic topic = this.context.getConfig().getTopicMap().get(topicName);
        if (null == topic) {
            return Result.error(String.format("主题[%s]不存在[db2es_id = %s]中", topicPartition, this.context.getConfig().getDb2EsId()));
        }
        this.context.getProcessorWrapper().stopTopic(topicPartition);
        this.context.getConfig().getTopicMap().remove(topicName);
        return Result.success(true);
    }
}