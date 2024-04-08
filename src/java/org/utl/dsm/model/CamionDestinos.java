package org.utl.dsm.model;

import java.util.List;

public class CamionDestinos {
    private int idCamion;
    private String nombreConductor;
    private List<String> destinos;
    private int estatus;
    private String qr;

    public CamionDestinos() {
    }

    public CamionDestinos(int idCamion, String nombreConductor, List<String> destinos, int estatus, String qr) {
        this.idCamion = idCamion;
        this.nombreConductor = nombreConductor;
        this.destinos = destinos;
        this.estatus = estatus;
        this.qr = qr;
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

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    
}
