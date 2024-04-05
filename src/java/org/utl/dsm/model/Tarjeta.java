package org.utl.dsm.model;

/**
 *
 * @author aleja
 */
public class Tarjeta {
    private int idTarjeta;
    private String numeroTarjeta;
    private String CVV;
    private float saldo;
    private String fechaCaducidad;
    private Usuario usuario;

    public Tarjeta(int idTarjeta, String numeroTarjeta, String CVV, float saldo, String fechaCaducidad, Usuario usuario) {
        this.idTarjeta = idTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.CVV = CVV;
        this.saldo = saldo;
        this.fechaCaducidad = fechaCaducidad;
        this.usuario = usuario;
    }

    public Tarjeta() {
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    
}
