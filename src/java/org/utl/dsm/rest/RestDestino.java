package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.controller.ControllerDestino;
import org.utl.dsm.model.Destino;

/**
 *
 * @author aleja
 */

@Path("destino")
public class RestDestino {
    @Path("agregarDestino")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarDestino(@FormParam("destino") String cadena){
        Gson gson = new Gson();
        
        String out="";
        try {
            Destino destino = gson.fromJson(cadena, Destino.class);
            ControllerDestino cd = new ControllerDestino();
            cd.agregarCamion(destino);
            out="""
                {"response":"El destino ha sido insertado y guardado con éxito."}
                """;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out="""
                {"response":"No se inserto el destino, vuelve a intentarlo"}
                """;
        }
        return Response.ok(out).build();
    }
    
}
