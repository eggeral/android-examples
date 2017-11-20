package com.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/echo")
public class EchoService {

    @GET
    @Produces("text/plain; charset=utf-8")
    @Path("alive")
    public String getAlive() {
        return "ok!";
    }

    @GET
    @Produces("text/plain; charset=utf-8")
    @Path("{msg}")
    public String echoGet(@PathParam("msg") String msg) {
        return msg;
    }

    @PUT
    @Produces("text/plain; charset=utf-8")
    public String echoPut(String msg) {
        return msg;
    }

    @POST
    @Produces("text/plain; charset=utf-8")
    @Path("{path}")
    public String echoPost(@PathParam("path") String path, String msg) {
        return "$path:$msg";
    }

    @DELETE
    @Produces("text/plain; charset=utf-8")
    @Path("{msg}")
    public Response echoDelete(@PathParam("msg") String msg) {
        return Response.ok().build();
    }

}
