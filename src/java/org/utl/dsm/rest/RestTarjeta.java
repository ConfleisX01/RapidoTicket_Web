package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
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
        String out = "";
        try {
            Tarjeta tarjeta = gson.fromJson(cadena, Tarjeta.class);
            ControllerTarjeta ct = new ControllerTarjeta();
            ct.agregarTarjeta(tarjeta);
            out = """
                {"response":"Tarjeta registrada"}
                """;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out = """
                {"response":"No se registro, vuelve a intentarlo"}
                """;
        }
        return Response.ok(out).build();
    }

    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllTarjetasPorUsuario(@QueryParam("idUsuario") int idUsuario) {
        String out = "";
        List<Tarjeta> tarjetas = null;
        ControllerTarjeta cc = new ControllerTarjeta();
        try {
            tarjetas = cc.getAllTarjetas(idUsuario);
            out = new Gson().toJson(tarjetas);
            System.out.println("zapato");
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                      {"error" : "Ocurrio un error, Intente mas tarde."}
                      """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("recargar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response recargarSaldo(@FormParam("idUsuario") int idUsuario, @FormParam("monto") float monto) {
        Gson gson = new Gson();
        String out = "";
        
        ControllerTarjeta ct = new ControllerTarjeta();
        
        try{
            
            ct.recargarSaldo(idUsuario, monto);
               out = """
                {"response":"correcto"}
                """;
        }catch(Exception e){
               System.out.println("mensaje de rest: " + e.getMessage());
          out = """
                {"response":"no correcto"}
                """;
        }
                return Response.status(Response.Status.OK).entity(out).build();
    }

}
