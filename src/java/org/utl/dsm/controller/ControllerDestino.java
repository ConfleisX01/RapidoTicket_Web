/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Destino;

/**
 *
 * @author aleja
 */
public class ControllerDestino {

    public void agregarDestino(String destino) {
        String query = "INSERT INTO destino(destinos) VALUES(?)";
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, destino);
            pstm.execute();
            pstm.close();
            connMysql.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Destino> getAllDestinos() {
        String query = "SELECT * FROM destino";
        List<Destino> lista = new ArrayList<>();
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Destino d = new Destino();
                d.setIdDestino(rs.getInt("idDestino"));
                d.setDestinos(rs.getString("destinos"));
                
                lista.add(d);
            }
            
            conn.close();
            return lista;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
