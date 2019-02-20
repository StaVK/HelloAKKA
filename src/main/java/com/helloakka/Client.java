package com.helloakka;

public class Client {
    private Processor processor;

    public void init(){
        processor=new Processor();
    }

    private String makeRequest(){
        return "Hello AKKA!";
    }

    private void run() throws Exception{
        for (int i = 0; i < 100; i++) {
            String response = processor.run(makeRequest());
            System.out.println(response);
        }

    }
    public static void main(String[] args) throws Exception {
        final Client client=new Client();
        client.init();
        client.run();
    }
}
