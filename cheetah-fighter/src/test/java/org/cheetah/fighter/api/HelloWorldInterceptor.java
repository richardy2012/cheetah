package org.cheetah.fighter.api;

import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.worker.Command;

/**
 * Created by Max on 2016/3/8.
 */
public class HelloWorldInterceptor implements Interceptor {
    @Override
    public boolean preHandle(Command command) throws Exception {
        System.out.println("prehandle");
        return true;
    }

    @Override
    public void postHandle(Command command) throws Exception {
        System.out.println("posthandle");
    }

    @Override
    public void afterCompletion(Command command, Exception ex) throws Exception {

    }

    @Override
    public boolean supportsType(Class<? extends Event> event) {
        return EventPublisherTest.ApplicationEventTest.class == event;
    }
}
