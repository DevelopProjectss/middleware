package org.wyyt.sharding.db2es.client.common;

import org.wyyt.sharding.db2es.client.entity.FlatMessge;

import java.util.List;

/**
 * the listener which invoked when kafka records arrivals.
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
public interface RecordListener {
    int consume(final List<FlatMessge> flatMessageList) throws Exception;
}