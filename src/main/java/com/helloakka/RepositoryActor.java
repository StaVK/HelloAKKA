package com.helloakka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class RepositoryActor extends UntypedAbstractActor {

    private ActorRef sender;


    public void save(String message) throws Exception{
        Thread.sleep(10);
        System.out.println("Save message: "+message);
    }
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            save(message.toString());
            sender.tell(message,sender());
        }
    }

    @Override
    public void preStart() throws Exception {
//        log.info("Start repository");
        sender=context().actorOf(Props.create(MailServiceActor.class));
    }
}
