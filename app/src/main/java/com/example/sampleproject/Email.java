package com.example.sampleproject;

public class Email {
    String id;
    String email;
    boolean validated;

    Email(String id, String email, boolean validated){
        this.id = id;
        this.email = email;
        this.validated = validated;
    }
}
