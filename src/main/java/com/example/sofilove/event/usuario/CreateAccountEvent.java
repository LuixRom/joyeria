package com.example.sofilove.event.usuario;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateAccountEvent extends ApplicationEvent {
    private final String email;
    private final String name;

    public CreateAccountEvent(Object source, String email, String name) {
        super(source);
        this.email = email;
        this.name = name;
    }
}
