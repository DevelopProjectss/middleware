package org.wyyt.db2es.core.entity.view;

import lombok.Data;
import org.wyyt.db2es.core.entity.domain.TopicOffset;

/**
 * the view entity of kafka's topic
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@Data
public final class TopicVo {
    private String topicName;
    private Boolean isActive;
    private String errorMsg;
    private Integer tps;
    private TopicOffset topicOffset;
    private String version;
}