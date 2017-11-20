package com.example;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestApplication extends Application {
    Set<Class<?>> classes = new HashSet<>();

    public RestApplication() {
        classes.add(EchoService.class);
        classes.add(Persons.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
