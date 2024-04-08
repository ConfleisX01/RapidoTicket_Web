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
import org.utl.dsm.controller.ControllerCamion;
import org.utl.dsm.model.CamionDestinos;

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
    
    @Path("asignarConductor")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response asignarConductor(@FormParam("nombreConductor") String nombreConductor,
            @FormParam("idCamion") int idCamion) {
        String out = "";
        ControllerCamion cc = new ControllerCamion();
        
        try {
            cc.asignarConductor(nombreConductor, idCamion);
            out = """
                  {"response" : "Conductor asignado"}
                  """;
        } catch(Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al asigar el conductor"}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("agregarDestinos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarDestinos(@FormParam("idCamion") int idCamion,
            @FormParam("posicionDestino") int posicionDestino,
            @FormParam("idDestino") int idDestino) {    
        String out = "";
        ControllerCamion cc = new ControllerCamion();
        try {
            cc.addDestinos(idCamion, posicionDestino, idDestino);
            out = """
                  {"response" : "Se agrego el destino con exito"}
                  """;
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"response" : "Error al agregar el destino"}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("agregarQr")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarQr(@FormParam("idCamion") int idCamion,
            @FormParam("qr") String qr) {
        String out = "";
        ControllerCamion cc = new ControllerCamion();
        
        try {
            cc.agregarQr(idCamion, qr);
            out = """
                  {"response" : "QR generado con exito"}
                  """;
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al generar el QR"}
                  """;
        }
        
        return Response.ok(out).build();
    }
    
    @Path("getAllCamiones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategorias() {
        String out = "";
        List<CamionDestinos> camiones = null;
        ControllerCamion cc = new ControllerCamion();
        Gson gson = new Gson();
        
        try {
            camiones = cc.getAll();
            out = new Gson().toJson(camiones);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al obtener todos los registros"}
                  """;
        }
        return Response.ok(out).build();
    }
}
