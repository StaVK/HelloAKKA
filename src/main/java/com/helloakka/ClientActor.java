package com.helloakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class ClientActor extends UntypedAbstractActor {
    private static ActorSystem system;

    private ActorRef processor;


    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            System.out.println(message);
        }
    }

    public static void main(String[] args) throws Exception{
        system=ActorSystem.create("ClientSystem");
        system.actorOf(Props.create(ClientActor.class));
//        system.actorOf(Props.create(ProcessorActor.class), "processor");

/*        system.actorOf(Props.create(RepositoryActor.class));
        system.actorOf(Props.create(MailServiceActor.class));*/
    }

    @Override
    public void preStart() throws Exception {
        processor=context().system().actorOf(Props.create(ProcessorActor.class), "processor");

        run();
    }

    private void run(){
        String request="Hello AKKA!";
        for (int i = 0; i < 100; i++) {
            processor.tell(request, self());
        }
    }
}
