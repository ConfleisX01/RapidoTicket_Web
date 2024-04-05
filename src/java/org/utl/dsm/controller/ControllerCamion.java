package org.utl.dsm.controller;

import org.utl.dsm.db.ConexionMysql;
import java.sql.Connection;
import java.sql.CallableStatement;

public class ControllerCamion {
    
    public void insertCamion(){
        String query = "call sp_insert_camion()";
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
}