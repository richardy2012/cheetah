package org.cheetah.fighter.governor.support;

import akka.actor.ActorRef;
import org.cheetah.fighter.governor.AbstractGovernor;
import org.cheetah.fighter.governor.Governor;
import org.cheetah.fighter.handler.Feedback;
import org.cheetah.fighter.worker.Command;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 2016/2/20.
 */
public class AkkaGovernor extends AbstractGovernor {
    private ActorRef worker;

    @Override
    public Governor reset() {
        super.reset();
        this.worker = null;
        return this;
    }

    public AkkaGovernor setWorker(ActorRef worker) {
        this.worker = worker;
        return this;
    }

    @Override
    protected Feedback notifyAllWorker() {
        if (handlerMap().isEmpty())
            return Feedback.EMPTY;
        Map<Class<? extends EventListener>, Feedback> feedbackMap = new HashMap<>();
        for (Class<? extends EventListener> clz : this.handlerMap().keySet()) {
            try {
                Command command = Command.of(details().event(), details().callback(), clz);
//                Future<Object> future = Patterns.ask(this.worker, command, 3000);
//                Object result = Await.result(future, Duration.create(3000, TimeUnit.MILLISECONDS));
//                if (result instanceof Feedback) {
//                    Feedback feedback = (Feedback) result;
//                    feedbackMap.put(clz, feedback);
//                }
                worker.tell(command, ActorRef.noSender());
            } catch (Exception e) {
                e.printStackTrace();
                feedbackMap.put(clz, Feedback.FAILURE);
            }
        }

//        if (!feedbackMap.isEmpty()) {
//            interceptorChain.pluginAll(feedbackMap);
//        }
        return Feedback.SUCCESS;
    }
}
