
package org.utl.dsm.controller;

/**
 *
 * @author joell
 */
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Persona;
import org.utl.dsm.model.Tarjeta;
import org.utl.dsm.model.Usuario;
import java.util.Random;

public class ControllerUsuario {


public Usuario loginUsuario(String usuario, String contrasenia) {
    String query = "SELECT * FROM usuario WHERE usuario = ? AND contrasenia = ?";
    Usuario u = new Usuario();
    Persona p = new Persona();

    try {
        ConexionMysql connMySQL = new ConexionMysql();
        Connection conn = connMySQL.open();
        PreparedStatement cstmt = conn.prepareStatement(query);

        cstmt.setString(1, usuario);
        
        cstmt.setString(2, contrasenia);

        ResultSet rs = cstmt.executeQuery();

        if (rs.next()) {
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setUsuario(rs.getString("usuario"));
            u.setContrasenia(rs.getString("contrasenia"));
            p.setIdPersona(rs.getInt("idPersona"));
            u.setPersona(p);

            return u;
        } else {
            return null;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


// Método para generar un número de tarjeta aleatorio
public String generarNumeroTarjeta() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    // Se generan 16 dígitos
    for (int i = 0; i < 16; i++) {
        sb.append(random.nextInt(10)); // Se generan dígitos aleatorios entre 0 y 9
    }
    return sb.toString();
}
    
// Método para insertar un usuario con tarjeta en la base de datos
    public Usuario insertUsuario(Usuario u) {
        String query = "CALL sp_insertar_usuario_con_tarjeta(?, ?, ?, ?, ?, ?, ?)";

        try {
            ConexionMysql connMySQL = new ConexionMysql();
            Connection conn = connMySQL.open();
            CallableStatement cstmt = (CallableStatement) conn.prepareCall(query);

            // Objetos que se usarán
            Persona persona = u.getPersona();
            Tarjeta tarjeta = u.getTarjeta();

            // Parámetros de entrada
            cstmt.setString(1, persona.getNombre()); // Utilizamos el nombre de persona como nombre de usuario
            cstmt.setString(2, persona.getApellidos());
            cstmt.setString(3, persona.getTelefono());
            cstmt.setString(4, persona.getNombre()); // Utilizamos el nombre de persona como nombre de usuario
            cstmt.setString(5, u.getContrasenia()); // No se convierte la contraseña
            cstmt.setString(6, generarNumeroTarjeta()); // Generar número de tarjeta automáticamente
            cstmt.setFloat(7, 0.0f); // Establecer el saldo como 0.0

            ResultSet rs = cstmt.executeQuery();

            // Leer los IDs generados y establecerlos en el objeto Usuario
            if (rs.next()) {
                int idPersona = rs.getInt("idPersona");
                int idUsuario = rs.getInt("idUsuario");
                int idTarjeta = rs.getInt("idTarjeta");

                u.getPersona().setIdPersona(idPersona);
                u.setIdUsuario(idUsuario);
                u.getTarjeta().setIdTarjeta(idTarjeta);
            }

            rs.close();
            cstmt.close();
            conn.close();
            connMySQL.close();

            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
