package org.utl.dsm.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.EmpleadoSesion;
import org.utl.dsm.model.Persona;

public class ControllerEmpleado {

    // TODO: Terminar la funcion para agregar el empleado
    public Empleado agregarEmpleado(Empleado e) {
        String queryInsertEmpleado = "CALL sp_insert_empleado(?, ?, ?, ?, ?, ?, ?)";
        String queryInsertEmpleadoSesion = "INSERT INTO empleadoSesion (idEmpleado, token) VALUES (?, '0')";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();

            // Insertar empleado
            CallableStatement cstmtInsertEmpleado = conn.prepareCall(queryInsertEmpleado);
            cstmtInsertEmpleado.setString(1, e.getPersona().getNombre());
            cstmtInsertEmpleado.setString(2, e.getPersona().getApellidos());
            cstmtInsertEmpleado.setString(3, e.getPersona().getTelefono());
            cstmtInsertEmpleado.setString(4, e.getNumeroEmpleado());
            cstmtInsertEmpleado.setString(5, e.getFoto());
            cstmtInsertEmpleado.registerOutParameter(6, java.sql.Types.INTEGER); // v_idPersona
            cstmtInsertEmpleado.registerOutParameter(7, java.sql.Types.INTEGER); // v_idEmpleado
            cstmtInsertEmpleado.execute();
            int idEmpleado = cstmtInsertEmpleado.getInt(7);

            cstmtInsertEmpleado.close();

            PreparedStatement pstmtInsertEmpleadoSesion = conn.prepareStatement(queryInsertEmpleadoSesion);
            pstmtInsertEmpleadoSesion.setInt(1, idEmpleado);
            pstmtInsertEmpleadoSesion.executeUpdate();

            pstmtInsertEmpleadoSesion.close();

            conn.close();
            connMySQL.close();
            return e;
        } catch (Exception ex) {
            ex.printStackTrace();
            return e;
        }
    }

    public EmpleadoSesion verificarLogin(String token) {
        String query = "SELECT * FROM view_empleadoSesion WHERE token = (?)";
        EmpleadoSesion es = new EmpleadoSesion();

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, token);
            
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                es.setIdSesion(rs.getInt("idSesion"));
                es.setIdEmpleado(rs.getInt("idEmpleado"));
                es.setNumeroEmpleado(rs.getString("numeroEmpleado"));
                es.setUsuario(rs.getString("usuario"));
                es.setContrasenia(rs.getString("contrasenia"));
                es.setToken(rs.getString("token"));

                System.out.println("IdSesion: " + es.getIdSesion());
                System.out.println("IdEmpleado: " + es.getIdEmpleado());
                System.out.println("Token: " + es.getToken());
                
                return es;
            }

            rs.close();
            pstmt.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public void registrarEmpleado(String usuario, String contrasenia, String numeroEmpleado) {
        String query = "UPDATE empleado SET usuario = (?), contrasenia = (?) WHERE numeroEmpleado = (?)";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);

            cstmt.setString(1, usuario);
            cstmt.setString(2, contrasenia);
            cstmt.setString(3, numeroEmpleado);

            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public String loginEmpleado(String usuario, String contrasenia) {
        String query = "SELECT * FROM view_empleadoSesion WHERE usuario = ? AND contrasenia = ?";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            PreparedStatement pstm = conn.prepareStatement(query);

            pstm.setString(1, usuario);
            pstm.setString(2, contrasenia);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                actualizarToken(rs.getInt("idEmpleado"));
                return rs.getString("token");
            }

            pstm.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public void actualizarToken(int idEmpleado) {
        String query = "UPDATE empleadoSesion SET token = (?) WHERE idEmpleado = (?)";
        String token = generarToken();

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);

            cstmt.setString(1, token);
            cstmt.setInt(2, idEmpleado);

            cstmt.execute();

            cstmt.close();
            conn.close();
            connMySQL.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String generarToken() {
        UUID uuid = UUID.randomUUID();

        String token = uuid.toString().replaceAll("-", "");

        return token;
    }

    public Empleado fill(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));
        e.setUsuario(rs.getString("usuario"));
        e.setContrasenia(rs.getString("contrasenia"));
        e.setFoto(rs.getString("foto"));
        Persona p = new Persona();
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setTelefono(rs.getString("telefono"));
        e.setPersona(p);
        return e;
    }

}
