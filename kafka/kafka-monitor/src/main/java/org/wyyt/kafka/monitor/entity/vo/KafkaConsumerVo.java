package org.wyyt.kafka.monitor.entity.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.apache.kafka.common.ConsumerGroupState;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * The view object for ajax's response. Using for show the kafka's consumer information.
 * <p>
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize         10/1/2020      Initialize   *
 * *****************************************************************
 */
@Data
@JsonSerialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class KafkaConsumerVo implements Serializable {
    private String groupId;
    private String node;
    private Integer topicCount;
    private Integer activeTopicCount;
    private Set<String> topicNames;
    private Set<String> activeTopicNames;
    private Set<String> notActiveTopicNames;
    private List<Meta> metaList;

    @Data
    @JsonSerialize
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Meta implements Serializable {
        private String consumerId;
        private String node;
        private List<TopicSubscriber> topicSubscriberList;
        private ConsumerGroupState consumerGroupState;
    }

    @Data
    @JsonSerialize
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class TopicSubscriber implements Serializable {
        private String topicName;
        private Integer partitionId;
    }

}
