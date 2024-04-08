package org.utl.dsm.controller;

import org.utl.dsm.db.ConexionMysql;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.utl.dsm.model.CamionDestinos;

public class ControllerCamion {

    public void insertCamion() {
        String query = "CALL sp_insert_camion()";
        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);

            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void asignarConductor(String nombreConductor, int idCamion) {
        String query = "UPDATE camion SET nombreConductor = (?) WHERE idCamion = (?)";
        
        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);
            
            cstmt.setString(1, nombreConductor);
            cstmt.setInt(2, idCamion);
            
            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addDestinos(int idCamion, int posicionDestino, int idDestino) {
        String query = "CALL sp_Insertar_CamionDestino(?, ?, ?)";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);

            System.out.println(idCamion + " " + posicionDestino + " " + idDestino);
            cstmt.setInt(1, idCamion);
            cstmt.setInt(2, posicionDestino);
            cstmt.setInt(3, idDestino);

            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void agregarQr(int idCamion, String qr) {
        String query = "UPDATE camion SET qr = (?) WHERE idCamion = (?)";
        
        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);
            
            cstmt.setString(1, qr);
            cstmt.setInt(2, idCamion);
            
            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<CamionDestinos> getAll() {
        String query = "SELECT * FROM view_camiones";
        List<CamionDestinos> destinos = new ArrayList<>();

        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                CamionDestinos cd = new CamionDestinos();
                cd.setIdCamion(rs.getInt("idCamion"));
                cd.setNombreConductor(rs.getString("nombreConductor"));
                cd.setEstatus(rs.getInt("estatus"));
                cd.setQr(rs.getString("qr"));
                String destinosString = rs.getString("destinos");
                String[] destinosArray = destinosString.split(",");
                List<String> destinosList = new ArrayList<>(Arrays.asList(destinosArray));
                cd.setDestinos(destinosList);
                
                destinos.add(cd);
            }
            conn.close();
            return destinos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
