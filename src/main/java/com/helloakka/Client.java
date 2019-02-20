package com.helloakka;


import org.perf4j.log4j.Log4JStopWatch;



public class Client implements Runnable{


    private Processor processor;

    public void init(){
        processor=new Processor();
    }

    private String makeRequest(){
        return "Hello AKKA!";
    }

    public void run(){

        try {

            for (int i = 0; i < 100; i++) {
                Log4JStopWatch stopWatch=new Log4JStopWatch("Client");
                String response = processor.run(makeRequest());
                System.out.println(response);
                stopWatch.stop();
            }

        }
        catch (Exception e){

        }


    }
    public static void main(String[] args) throws Exception {
        final Client client=new Client();
        client.init();
        Long time=System.currentTimeMillis();
        new Thread(client).start();
        new Thread(client).start();
        client.run();

        System.out.println("TOTAL TIME: "+ (System.currentTimeMillis()-time));
    }
}
