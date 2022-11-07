package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Controller.gestionarConexion;

public class ComicsDao {

    private Connection con;

    public ComicsDao() {
        con = gestionarConexion.getConexion();
    }

    public ArrayList<Comics> getListaComics() {
        ArrayList<Comics> listaComics = new ArrayList<Comics>();

        try {
            String consulta = "SELECT * FROM comics ORDER BY titulo";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            ResultSet tablas = gestionarConexion.getConexion().getMetaData().getTables(null, null, "comics", null);
            if (tablas.next()) {
                ResultSet res = sentencia.executeQuery(consulta);
                while (res.next()) {
                    Comics c = new Comics(res.getInt(1), res.getString(2), res.getBlob(3),
                            res.getDate(4), res.getInt(5),
                            res.getFloat(6), res.getString(7));
                    listaComics.add(c);
                }
                res.close();
            }

            sentencia.close();
            return listaComics;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar los Cómics");
            return null;
        }
    }

    public void removeComic(String tituloComic, int num_coleccion) {
        try {
            String consulta = "DELETE FROM comics WHERE titulo = ? AND num_coleccion = ?";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setString(1, tituloComic);
            sentencia.setInt(2, num_coleccion);
            sentencia.executeUpdate();

            gestionarConexion.getConexion().commit();

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                gestionarConexion.getConexion().rollback();
                JOptionPane.showMessageDialog(null, "Error al modificar el Cómic");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
