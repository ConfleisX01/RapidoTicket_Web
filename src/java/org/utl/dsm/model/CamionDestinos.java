package org.utl.dsm.model;

import java.util.List;

public class CamionDestinos {
    private int idCamion;
    private List<String> destinos;
    private int idEmpleado;
    private String nombreConductor;
    private int estatus;
    private String qr;

    public CamionDestinos() {
    }

    public CamionDestinos(int idCamion, List<String> destinos, int idEmpleado, String nombreConductor, int estatus, String qr) {
        this.idCamion = idCamion;
        this.destinos = destinos;
        this.idEmpleado = idEmpleado;
        this.nombreConductor = nombreConductor;
        this.estatus = estatus;
        this.qr = qr;
    }

    public int getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    public List<String> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<String> destinos) {
        this.destinos = destinos;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
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
