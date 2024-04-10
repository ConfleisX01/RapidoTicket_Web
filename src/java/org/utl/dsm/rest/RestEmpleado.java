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
import org.utl.dsm.controller.ControllerEmpleado;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.EmpleadoSesion;

/**
 *
 * @author aleja
 */
@Path("empleado")
public class RestEmpleado {

    @Path("agregarEmpleado")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarEmpleado(@FormParam("empleado") String cadena) {
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();
        Gson gson = new Gson();
        try {
            Empleado empleado = gson.fromJson(cadena, Empleado.class);
            System.out.println("Datos del emmpleado JSON: " + cadena);
            ce.agregarEmpleado(empleado);
            out = """
                  {"response":"Empleado agregado exitosamente."}
                  """;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out = """
                {"response":"No se registro, vuelve a intentarlo."}
                """;
        }
        return Response.ok(out).build();
    }

    @Path("registrarEmpleado")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarEmpleado(@FormParam("usuario") String usuario,
            @FormParam("contrasenia") String contrasenia,
            @FormParam("numeroEmpleado") String numeroEmpleado) {
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();

        try {
            ce.registrarEmpleado(usuario, contrasenia, numeroEmpleado);
            out = """
                  {"response" : "Empleado registrado con exito"}
                  """;
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al registrar el empleado"}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("loginEmpleado")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginEmpleado(@QueryParam("usuario") String usuario,
            @QueryParam("contrasenia") String contrasenia) {
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();
        Gson gson = new Gson();
        
        try {
            if (ce.loginEmpleado(usuario, contrasenia) != null) {
                out = gson.toJson(ce.loginEmpleado(usuario, contrasenia));
            } else {
                out = """
                      {"response" : "Error"}
                      """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al iniciar sesion"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("verificarLogin")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarLogin(@QueryParam("token") String token) {
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();
        Gson gson = new Gson();
        EmpleadoSesion es = new EmpleadoSesion();

        try {
            out = gson.toJson(ce.verificarLogin(token));
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"response" : "Error al verificar el login"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("getAllEmpleados")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAll() {
        String out = null;
        List<Empleado> empleados = null;
        ControllerEmpleado ce = new ControllerEmpleado();
        try {
            empleados = ce.getAll();
            out = new Gson().toJson(empleados);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                        {"error":"Ocurrio un problema, intentelo nuevamente."}
                    """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
