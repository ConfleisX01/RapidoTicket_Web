package org.utl.dsm.model;

public class Empleado {
    private int idEmpleado;
    private String usuario;
    private String contrasenia;
    private Persona persona;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String usuario, String contrasenia, Persona persona) {
        this.idEmpleado = idEmpleado;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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
}
