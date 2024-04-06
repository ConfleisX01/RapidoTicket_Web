package org.utl.dsm.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Empleado;

public class ControllerEmpleado {

    // TODO: Terminar la funcion para agregar el empleado
    public Empleado agregarEmpleado(Empleado e) {
        String query = "CALL sp_insert_empleado(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            System.out.println(e.getNumeroEmpleado() == null ? "Esta nulo" : "Contiene algo");
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);
            cstmt.setString(1, e.getPersona().getNombre());
            cstmt.setString(2, e.getPersona().getApellidos());
            cstmt.setString(3, e.getPersona().getTelefono());
            cstmt.setString(4, e.getNumeroEmpleado());
            cstmt.setString(5, e.getUsuario());
            cstmt.setString(6, e.getContrasenia());
            
            System.out.println("numeroEmpleado " + e.getNumeroEmpleado());
            
            cstmt.registerOutParameter(7, java.sql.Types.INTEGER);
            cstmt.registerOutParameter(8, java.sql.Types.INTEGER);
            
            cstmt.execute();
            cstmt.close();
            conn.close();
            connMySQL.close();
            return e;
        } catch (Exception ex) {
            ex.printStackTrace();
            return e;
        }
    }
}
