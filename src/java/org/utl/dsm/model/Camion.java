package org.utl.dsm.model;

public class Camion {

    private int idCamion;
    private String nombreConductor;
    private String qr;
    private int estatus;

    public Camion() {
    }

    public Camion(int idCamion, String nombreConductor, String qr, int estatus) {
        this.idCamion = idCamion;
        this.nombreConductor = nombreConductor;
        this.qr = qr;
        this.estatus = estatus;
    }

    public int getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    
    
}
