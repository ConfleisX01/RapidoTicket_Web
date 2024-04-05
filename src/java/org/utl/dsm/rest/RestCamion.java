package org.utl.dsm.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("camion")
public class RestCamion extends Application{
    @Path("saludar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response saludar() {
        String out = """
                     {"response" : "Hola desde la API Camiones"}
                     """;
        return Response.ok(out).build();
    }
}
