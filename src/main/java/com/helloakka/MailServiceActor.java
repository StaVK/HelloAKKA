package com.helloakka;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

public class MailServiceActor extends UntypedAbstractActor {

    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            send(message.toString());
            sender().tell(message, ActorRef.noSender());
        }
    }

    @Override
    public void preStart() throws Exception {
//        log.info("Start sender");
    }

    private void send(String message){
        // Very hard work
        System.out.println("Send message: "+ message);
    }
}
