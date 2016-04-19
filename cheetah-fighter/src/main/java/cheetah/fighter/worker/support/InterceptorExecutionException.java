package cheetah.fighter.worker.support;


import cheetah.commons.PlatformException;

/**
 * Created by Max on 2016/3/8.
 */
public class InterceptorExecutionException extends PlatformException {
    public InterceptorExecutionException() {
    }

    public InterceptorExecutionException(String message) {
        super(message);
    }

    public InterceptorExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterceptorExecutionException(Throwable cause) {
        super(cause);
    }

    public InterceptorExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
