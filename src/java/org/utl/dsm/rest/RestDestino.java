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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public Response agregarDestino(@FormParam("destino") String destino) {
        Gson gson = new Gson();

        String out = "";
        try {
            ControllerDestino cd = new ControllerDestino();
            cd.agregarDestino(destino);
            out = """
                {"response":"El destino ha sido insertado y guardado con éxito."}
                """;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out = """
                {"response":"No se inserto el destino, vuelve a intentarlo."}
                """;
        }
        return Response.ok(out).build();
    }

    @Path("getAllDestinos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDestinos() {
        String out = "";
        List<Destino> lista = new ArrayList<>();
        ControllerDestino cd = new ControllerDestino();

        try {
            Gson gson = new Gson();
            lista = cd.getAllDestinos();
            out = gson.toJson(lista);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al obtener la lista de destinos."}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("destinosPorCamion")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinosPorCamion(@QueryParam("idCamion") int idCamion) {
        var out = "";
        var controller = new ControllerDestino();

        var gson = new Gson();
        List<Destino> destinos = null;
        try {
            destinos = controller.getDestinosPorCamion(idCamion);
            out = gson.toJson(destinos);
            System.out.println("tenis");

            // Verificar si la lista de destinos está vacía y devolver un mensaje apropiado
            if (destinos.isEmpty()) {
                out = """
                      {"response" : "No se encontraron destinos para el camión especificado."}
                      """;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al obtener la lista de destinos."}
                  """;
        }
        return Response.ok(out).build();
    }
}
