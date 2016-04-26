package org.cheetah.fighter.core;

import org.cheetah.commons.PlatformException;

/**
 * Created by Max on 2016/2/23.
 */
public class NoMapperException extends PlatformException {
    public NoMapperException() {
        super("Couldn't find the corresponding mapping.");
    }
}