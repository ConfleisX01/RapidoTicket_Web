package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.controller.ControllerTarjeta;
import org.utl.dsm.model.Tarjeta;

/**
 *
 * @author aleja
 */
@Path("tarjeta")
public class RestTarjeta {
    @Path("agregarTarjeta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarTarjeta(@FormParam("tarjeta") String cadena) {
        Gson gson = new Gson();
        String out="";
        try {
            Tarjeta tarjeta = gson.fromJson(cadena, Tarjeta.class);
            ControllerTarjeta ct = new ControllerTarjeta();
            ct.agregarTarjeta(tarjeta);
            out="""
                {"response":"Tarjeta registrada"}
                """;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out="""
                {"response":"No se registro, vuelve a intentarlo"}
                """;
        }
         return Response.ok(out).build();
    }
    
}
