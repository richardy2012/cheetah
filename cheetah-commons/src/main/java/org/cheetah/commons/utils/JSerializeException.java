package org.cheetah.commons.utils;

import org.cheetah.commons.PlatformException;

/**
 * Created by Max on 2016/2/28.
 */
public class JSerializeException extends PlatformException {
    private static final long serialVersionUID = 2336359841576111685L;

    public JSerializeException() {
        super("");
    }

    public JSerializeException(String message) {
        super(message);
    }

    public JSerializeException(Throwable cause) {
        super("", cause);
    }

    public JSerializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JSerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
