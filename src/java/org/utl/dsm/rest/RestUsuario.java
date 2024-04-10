/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.rest;


import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.controller.ControllerUsuario;
import org.utl.dsm.model.Usuario;

@Path("usuario")
public class RestUsuario extends Application {

    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response login(
            @QueryParam("usuario") @DefaultValue("") String usuario,
            @QueryParam("contrasenia") @DefaultValue("") String contrasenia
    ) {

        ControllerUsuario cu = new ControllerUsuario();
        Usuario u = cu.loginUsuario(usuario, contrasenia);
        Gson gson = new Gson();
        String out = "";
        if (u != null) {
            out = gson.toJson(u);
        } else {
            out = """
                    {"response" : "null"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("InsertUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response insertUsuario(@FormParam("usuario") @DefaultValue("") String u) {

        String out = "";

        ControllerUsuario cu = new ControllerUsuario();
        Gson gson = new Gson();
        try {

            //datos del usuario en json
            Usuario usuario = gson.fromJson(u, Usuario.class);

            cu.insertUsuario(usuario);
            System.out.println("este es del rest:" + usuario.getPersona().getNombre());
            out = """
                  {"response" : "operacion exitosa"}
                  """;
            out = String.format(out, u);
        }// fin del try
        catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"response" : "Error en la transacción"}
                  """;
        }
        return Response.status(Response.Status.CREATED).entity(out).build();
    }
    
    @Path("getAllUsuarios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuarios() {
        String out = "";
        List<Usuario> usuarios = null;
        ControllerUsuario cu = new ControllerUsuario();
        
        try {
            usuarios = cu.getAllUsuarios();
            out = new Gson().toJson(usuarios);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"response" : "Error al obtener todos los usuarios"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
