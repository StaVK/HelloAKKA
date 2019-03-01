package com.helloakka;

import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ClientActor extends UntypedAbstractActor {
    private static ActorSystem system;

    private ActorSelection processor;


    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            System.out.println(message);
        }
    }

    public static void main(String[] args) throws Exception{
        final Config config= ConfigFactory.load().getConfig("client");

        system=ActorSystem.create("ClientSystem", config);
        system.actorOf(Props.create(ClientActor.class));
    }

    @Override
    public void preStart() throws Exception {
//        processor=context().system().actorOf(Props.create(ProcessorActor.class), "processor");
        final String path="akka.tcp://ProcessorSystem@127.0.0.1:2551/user/processor";
//        processor=system.actorSelection(path);
        processor=context().system().actorSelection(path);

        run();
    }

    private void run(){
        String request="Hello AKKA!";
        for (int i = 0; i < 10; i++) {
            processor.tell(request, self());
        }
    }

    @Override
    public void postStop() throws Exception {
        processor.tell(PoisonPill.getInstance(),ActorRef.noSender());

    }

    // Comment for test
}
