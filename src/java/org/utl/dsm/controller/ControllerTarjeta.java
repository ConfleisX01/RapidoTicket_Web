package org.utl.dsm.controller;

import org.utl.dsm.db.ConexionMysql;
import org.utl.dsm.model.Tarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

   public List<Tarjeta> getAllTarjetas(int idUsuario) throws SQLException {
    String query = "SELECT * FROM tarjeta WHERE idUsuario = ?";
    
    // Inicializar la lista de tarjetas
    List<Tarjeta> tarjetas = new ArrayList<>();
    
    // Establecer la conexión a la base de datos
    ConexionMysql connMySQL = new ConexionMysql();
    Connection conn = connMySQL.open();
    
    // Preparar y ejecutar la consulta
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, idUsuario); // Establecer el valor del parámetro idUsuario en la consulta
        try (ResultSet rs = pstmt.executeQuery()) {
            // Recorrer los resultados y llenar la lista de tarjetas
            while (rs.next()) {
                tarjetas.add(fill(rs));
            }
        }
    } finally {
        // Cerrar la conexión a la base de datos
        connMySQL.close();
    }
    
    // Retornar la lista de tarjetas
    return tarjetas;
}

// Método auxiliar para llenar un objeto Tarjeta desde un ResultSet
private Tarjeta fill(ResultSet rs) throws SQLException {
    Tarjeta tarjeta = new Tarjeta();
    
    tarjeta.setIdTarjeta(rs.getInt("idTarjeta"));
    tarjeta.setNumeroTarjeta(rs.getString("numeroTarjeta"));
    tarjeta.setCVV(rs.getString("CVV"));
    tarjeta.setFechaCaducidad(rs.getString("fechaCaducidad"));
    tarjeta.setSaldo(rs.getFloat("saldo"));
    
    return tarjeta;
}

 public void recargarSaldo(int idUsuario, float montoRecarga) {
        String query = "CALL sp_insertar_saldo_en_tarjeta_por_usuario(?, ?)";
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idUsuario);
            pstm.setFloat(2, montoRecarga);

            pstm.execute();
            System.out.println("Saldo recargado exitosamente.");
            pstm.close();
            connMysql.close();
        } catch (Exception e) {
            System.out.println("Error al recargar saldo: " + e.getMessage());
        }
    }

}
