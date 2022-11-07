package Model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Controller.gestionarConexion;

public class ComicsDao {

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

    public void addComic(
            String titulo,
            int num_coleccion,
            float precio,
            int cantidad,
            FileInputStream img,
            File portada,
            Date fecha,
            String estado) {
        try {
            String consulta = "INSERT INTO comics(num_coleccion,titulo,portada,fechaAdquisicion,cantidadStock,precio,estado) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setInt(1, num_coleccion);
            sentencia.setString(2, titulo);
            sentencia.setBinaryStream(3, img, (int) portada.length());
            sentencia.setDate(4, fecha);
            sentencia.setInt(5, cantidad);
            sentencia.setFloat(6, precio);
            sentencia.setString(7, estado);
            sentencia.executeUpdate();

            img.close();

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el Cómic");
        }
    }

    public void updateComic(
            String titulo,
            int num_coleccion,
            float precio,
            int cantidad,
            FileInputStream img,
            File portada,
            Date fecha,
            String estado) {
        try {
            String consulta = "UPDATE comics SET portada = ?,fechaAdquisicion = ?,cantidadStock = ?,precio = ?,estado = ? WHERE num_coleccion = ? AND titulo = ?";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setBinaryStream(1, img, (int) portada.length());
            sentencia.setDate(2, fecha);
            sentencia.setInt(3, cantidad);
            sentencia.setFloat(4, precio);
            sentencia.setString(5, estado);
            sentencia.setInt(6, num_coleccion);
            sentencia.setString(7, titulo);
            sentencia.executeUpdate();

            img.close();
            gestionarConexion.getConexion().commit();

        } catch (SQLException | IOException ex) {
            try {
                ex.printStackTrace();
                gestionarConexion.getConexion().rollback();
                JOptionPane.showMessageDialog(null, "Error al insertar el Cómic");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Comics searchComic(String titulo) {
        Comics c = null;
        try {
            String consulta = "SELECT * FROM comics WHERE titulo = ?";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setString(1, titulo);
            ResultSet res = sentencia.executeQuery();
            if (res.next()) {
                c = new Comics(res.getInt(1), res.getString(2), res.getBlob(3),
                        res.getDate(4), res.getInt(5),
                        res.getFloat(6), res.getString(7));
            }
            res.close();

            sentencia.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar el Cómics");
        }
        return c;
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
                JOptionPane.showMessageDialog(null, "Error al eliminar el Cómic");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
