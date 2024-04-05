package org.utl.dsm.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.controller.ControllerCamion;

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
    
    // Funcion para agregar un camion
    @Path("agregarCamion")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarCamion() {
        String out = "";
        ControllerCamion cc = new ControllerCamion();
        try {
            cc.insertCamion();
            out = """
                  {"response" : "Se agrego el camion con exito"}
                  """;
        } catch(Exception e) {
            e.printStackTrace();
            out = """
                  {"response" : "Error al agregar el camion"}
                  """;
        }
        return Response.ok(out).build();
    }
}
