package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persons")
public class Persons {

    private Logger logger = LogManager.getLogger(Person.class);
    private static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person("Fritz", "Berlin"));
        persons.add(new Person("Kart", "Bern"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons() {
        logger.info("Get " + persons);

        return persons;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person addPerson(Person person) {

        logger.info("Add " + person);
        persons.add(person);
        return person;

    }

}
