package org.utl.dsm.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Mensaje;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ControllerMensajes {

    public void agregarMensaje(int idEmpleado, String mensaje) {
        String query = "INSERT INTO mensajes (idEmpleado, mensaje) VALUES (?, ?)";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = conn.prepareCall(query);

            cstmt.setInt(1, idEmpleado);
            cstmt.setString(2, mensaje);

            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Mensaje> getAll() {
        String query = "SELECT * FROM view_mensajes";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            List<Mensaje> m = new ArrayList<>();

            while (rs.next()) {
                Mensaje mensaje = new Mensaje();
                mensaje.setIdMensaje(rs.getInt("idMensaje"));
                mensaje.setIdEmpleado(rs.getInt("idEmpleado"));
                mensaje.setMensaje(rs.getString("mensaje"));
                mensaje.setFoto(rs.getString("foto_empleado"));
                m.add(mensaje);
            }
            rs.close();
            pstmt.close();
            connMySQL.close();
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
