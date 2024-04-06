package org.utl.dsm.controller;

import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Tarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.utl.dsm.model.Usuario;

/**
 *
 * @author aleja
 */
public class ControllerTarjeta {

    //Método para agregar una tarjeta
    public Tarjeta agregarTarjeta(Tarjeta t) {
        //String query = "INSERT INTO tarjeta (numeroTarjeta, CVV, saldo, fechaCaducidad, idUsuario) VALUES (?, ?, ?, ?, ?)";
        String query = "CALL sp_insert_tarjeta(?, ?, ?, ?, ?)";
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, t.getNumeroTarjeta());
            pstm.setString(2, t.getCVV());
            pstm.setFloat(3, t.getSaldo());
            pstm.setString(4, t.getFechaCaducidad());
            pstm.setInt(5, t.getUsuario().getIdUsuario());
            pstm.execute();
            System.out.println("Tarjeta registrada exitosamente.");
            pstm.close();
            connMysql.close();
            return t;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return t;
        }

    }
}
