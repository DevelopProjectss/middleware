package org.wyyt.sharding.db2es.client.core;

import org.wyyt.sharding.db2es.client.common.Context;
import org.wyyt.sharding.db2es.client.common.RecordListener;
import org.wyyt.sharding.db2es.client.entity.FlatMessge;

import java.util.List;

/**
 * the default implementation of RecordListener
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
public class RecordListenerImpl implements RecordListener {
    private final Context context;

    public RecordListenerImpl(final Context context) {
        this.context = context;
    }

    @Override
    public int consume(final List<FlatMessge> flatMessageList) throws Exception {
        if (null == flatMessageList || flatMessageList.isEmpty()) {
            return 0;
        }
        int result = this.context.getElasticSearchWrapper().populate(flatMessageList);
        final FlatMessge lastFlatMessage = flatMessageList.get(flatMessageList.size() - 1);
        lastFlatMessage.commit(this.context);
        return result;
    }
}