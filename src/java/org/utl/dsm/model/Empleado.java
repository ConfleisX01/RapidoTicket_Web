package org.utl.dsm.model;

public class Empleado {
    private int idEmpleado;
    private String numeroEmpleado;
    private String usuario;
    private String contrasenia;
    private Persona persona;

    public Empleado(int idEmpleado, String numeroEmpleado, String usuario, String contrasenia, Persona persona) {
        this.idEmpleado = idEmpleado;
        this.numeroEmpleado = numeroEmpleado;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.persona = persona;
    }

    public Empleado() {
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    
}
