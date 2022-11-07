package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controller.gestionarConexion;

public class ColeccionesDao {

    private Connection con;

    public ColeccionesDao() {
        con = gestionarConexion.getConexion();
    }

    public Colecciones getColeccion(int numCol) {
        Colecciones col = new Colecciones();

        try {
            String consulta = "SELECT * FROM colecciones WHERE num_coleccion = ?";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setInt(1, numCol);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                col = new Colecciones(resultado.getInt(1), resultado.getString(2));
            }

            sentencia.close();
            con.close();
            return col;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
