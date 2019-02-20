package com.helloakka;

import static java.lang.String.format;

public class Processor {

    private int count=0;

    private MailService mailService;

    private Repository repository;

    public Processor() {
        init();
    }
    public void init(){
        this.mailService = new MailService();
        this.repository = new Repository();
    }

    public synchronized String run(String request) throws Exception{
        final String message=format("%d: %s", count++, request);
        repository.save(message);
        mailService.send(message);

        return message;
    }
}
