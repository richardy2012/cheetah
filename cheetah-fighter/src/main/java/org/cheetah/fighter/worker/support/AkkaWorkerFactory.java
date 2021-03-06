package org.cheetah.fighter.worker.support;

import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.worker.WorkerFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorkerFactory implements WorkerFactory {

    @Override
    public Worker createWorker() {
        AkkaWorkerAdaptor adaptor = new AkkaWorkerAdaptor(new AkkaWorker());
        return adaptor;
    }

}
