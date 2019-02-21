package com.helloakka;

import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import static java.lang.String.format;

public class ProcessorActor extends UntypedAbstractActor {
    private static ActorSystem system;
    private ActorRef repository;

    private int count = 0;


    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            final String response = format("%d: %s", count++, message);
            repository.tell(response, sender());
        }
    }

    public static void main(String[] args) throws Exception {
        final Config config= ConfigFactory.load().getConfig("processor");

        system = ActorSystem.create("ProcessorSystem",config);
        system.actorOf(Props.create(ProcessorActor.class),"processor");
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
