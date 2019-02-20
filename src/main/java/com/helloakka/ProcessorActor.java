package com.helloakka;

import akka.actor.*;

import static java.lang.String.format;

public class ProcessorActor extends UntypedAbstractActor {
    private ActorRef repository;

    private int count = 0;


    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            final String response = format("%d: %s", count++, message);
            repository.tell(response, sender());
        }
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("ProcessorSystem");
//        system.actorOf(Props.create(ProcessorActor.class));
    }

    @Override
    public void preStart() throws Exception {

//        log.info("Start processor");
        repository = context().actorOf(Props.create(RepositoryActor.class));
    }

    @Override
    public void postStop() throws Exception {
        repository.tell(PoisonPill.getInstance(), ActorRef.noSender());
//        log.info("Stop processor");
    }
}
