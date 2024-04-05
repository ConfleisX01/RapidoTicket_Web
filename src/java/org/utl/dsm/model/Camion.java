package org.utl.dsm.model;

public class Camion {

    private int idCamion;
    private String nombreConductor;
    private int estatus;

    public Camion() {
    }

    public Camion(int idCamion, String nombreConductor, int estatus) {
        this.idCamion = idCamion;
        this.nombreConductor = nombreConductor;
        this.estatus = estatus;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
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

}
