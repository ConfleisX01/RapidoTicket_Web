package org.utl.dsm.model;

import java.util.List;

public class CamionDestinos {
    private int idCamion;
    private String nombreConductor;
    private List<String> destinos;
    private int estatus;

    public CamionDestinos() {
    }

    public CamionDestinos(int idCamion, String nombreConductor, List<String> destinos, int estatus) {
        this.idCamion = idCamion;
        this.nombreConductor = nombreConductor;
        this.destinos = destinos;
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

    public List<String> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<String> destinos) {
        this.destinos = destinos;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}
