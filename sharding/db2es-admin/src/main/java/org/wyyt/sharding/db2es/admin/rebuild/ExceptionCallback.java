package org.wyyt.sharding.db2es.admin.rebuild;

/**
 * The interface of exception callback
 * <p>
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
public interface ExceptionCallback {
    Exception getException();

    void setException(final Exception exception);

    void setExceptionPropertyChanged(final ExceptionPropertyChanged exceptionPropertyChanged);

    interface ExceptionPropertyChanged {
        void changed(final Exception exception);
    }
}