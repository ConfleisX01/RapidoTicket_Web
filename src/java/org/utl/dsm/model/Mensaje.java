package org.utl.dsm.model;

public class Mensaje {

    private int idMensaje;
    private int idEmpleado;
    private String mensaje;
    private String foto;

    public Mensaje() {
    }

    public Mensaje(int idMensaje, int idEmpleado, String mensaje, String foto) {
        this.idMensaje = idMensaje;
        this.idEmpleado = idEmpleado;
        this.mensaje = mensaje;
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
