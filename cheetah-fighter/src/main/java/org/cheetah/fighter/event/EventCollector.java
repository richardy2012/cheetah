package org.cheetah.fighter.event;

/**
 * 事件收集器
 * Created by Max on 2016/2/1.
*/
public interface EventCollector {

    void collect(Event event);

    void collect(Event event, Callback callback);

}
