package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.controller.ControllerMensajes;
import org.utl.dsm.model.Mensaje;

@Path("mensajes")
public class RestMensajes extends Application {

    @Path("agregarMensaje")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarMensaje(@FormParam("idEmpleado") int idEmpleado,
            @FormParam("mensaje") String mensaje) {
        String out = "";
        ControllerMensajes cm = new ControllerMensajes();

        try {
            cm.agregarMensaje(idEmpleado, mensaje);
            out = """
                  {"response" : "Se agrego el mensaje con exito"}
                  """;
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al agregar el mensaje"}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("getAllMensajes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMensajes() {
        String out = "";
        List<Mensaje> mensajes = null;
        ControllerMensajes cm = new ControllerMensajes();
        
        try {
            mensajes = cm.getAll();
            out = new Gson().toJson(mensajes);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"response" : "Error al obtener todos los mensajes"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
