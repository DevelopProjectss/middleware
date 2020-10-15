package org.wyyt.db2es.client.common;

/**
 * the user-defined function used for how to commit check-point
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
public interface UserCommitCallBack {
    void commit(final CheckpointExt checkpoint);
}