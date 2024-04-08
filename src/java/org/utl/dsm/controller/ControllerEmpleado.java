package org.utl.dsm.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Persona;

public class ControllerEmpleado {

    // TODO: Terminar la funcion para agregar el empleado
    public Empleado agregarEmpleado(Empleado e) {
        String query = "CALL sp_insert_empleado(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            //System.out.println(e.getNumeroEmpleado() == null ? "Esta nulo" : "Contiene algo");
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);
            cstmt.setString(1, e.getPersona().getNombre());
            cstmt.setString(2, e.getPersona().getApellidos());
            cstmt.setString(3, e.getPersona().getTelefono());
            cstmt.setString(4, e.getNumeroEmpleado());
            cstmt.setString(5, e.getUsuario());
            cstmt.setString(6, e.getContrasenia());
            cstmt.setString(7, e.getFoto());

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

    public List<Empleado> getAll() throws SQLException {
        String sql = "SELECT * FROM view_empleado";
        ConexionMysql connMySQL = new ConexionMysql();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Empleado> empleado = new ArrayList<>();
        while (rs.next()) {
            empleado.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();
        return empleado;
    }
    
    public Empleado fill(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));
        e.setUsuario(rs.getString("usuario"));
        e.setContrasenia(rs.getString("contrasenia"));
        Persona p = new Persona();
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setTelefono(rs.getString("telefono"));
        e.setPersona(p);
        return e;
    }

}
