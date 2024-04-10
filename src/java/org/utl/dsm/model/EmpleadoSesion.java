package org.utl.dsm.model;

public class EmpleadoSesion {

    private int idSesion;
    private int idEmpleado;
    private String numeroEmpleado;
    private String usuario;
    private String contrasenia;
    private String token;

    public EmpleadoSesion() {
    }

    public EmpleadoSesion(int idSesion, int idEmpleado, String numeroEmpleado, String usuario, String contrasenia, String token) {
        this.idSesion = idSesion;
        this.idEmpleado = idEmpleado;
        this.numeroEmpleado = numeroEmpleado;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.token = token;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
