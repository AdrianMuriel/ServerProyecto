package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;
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

    public int getComic(String titulo) {
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

            if (c == null) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al comprobar el Cómics");
            return 0;
        }
    }

    public void addComic(
            String titulo,
            int num_coleccion,
            float precio,
            int cantidad,
            Date fecha,
            String estado,
            File img) {
        try {
            byte[] byteBlob = Files.readAllBytes(img.toPath());
            Blob imgB = new SerialBlob(byteBlob);

            String consulta = "INSERT INTO comics(num_coleccion, titulo, portada, fechaAdquisicion, cantidadStock, precio, estado) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setInt(1, num_coleccion);
            sentencia.setString(2, titulo);
            sentencia.setBlob(3, imgB);
            sentencia.setDate(4, fecha);
            sentencia.setInt(5, cantidad);
            sentencia.setFloat(6, precio);
            sentencia.setString(7, estado);
            sentencia.executeUpdate();
            gestionarConexion.getConexion().commit();
        } catch (Exception ex) {
            try {
                gestionarConexion.getConexion().rollback();
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al insertar el Cómic");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateComic(
            String titulo,
            int num_coleccion,
            float precio,
            int cantidad,
            File img,
            Date fecha,
            String estado) {
        try {
            byte[] byteBlob = Files.readAllBytes(img.toPath());
            Blob imgB = new SerialBlob(byteBlob);

            String consulta = "UPDATE comics SET portada = ?,fechaAdquisicion = ?,cantidadStock = ?,precio = ?,estado = ? WHERE num_coleccion = ? AND titulo = ?";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setBlob(1, imgB);
            sentencia.setDate(2, fecha);
            sentencia.setInt(3, cantidad);
            sentencia.setFloat(4, precio);
            sentencia.setString(5, estado);
            sentencia.setInt(6, num_coleccion);
            sentencia.setString(7, titulo);
            sentencia.executeUpdate();

            gestionarConexion.getConexion().commit();

        } catch (SQLException | IOException ex) {
            try {
                ex.printStackTrace();
                gestionarConexion.getConexion().rollback();
                JOptionPane.showMessageDialog(null, "Error al modificar el Cómic");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateComicNoImage(
            String titulo,
            int num_coleccion,
            float precio,
            int cantidad,
            Date fecha,
            String estado) {
        try {
            String consulta = "UPDATE comics SET fechaAdquisicion = ?,cantidadStock = ?,precio = ?,estado = ? WHERE num_coleccion = ? AND titulo = ?";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            sentencia.setDate(1, fecha);
            sentencia.setInt(2, cantidad);
            sentencia.setFloat(3, precio);
            sentencia.setString(4, estado);
            sentencia.setInt(5, num_coleccion);
            sentencia.setString(6, titulo);
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

    public static byte[] convertFileContentToBlob(String filePath) throws IOException {
        byte[] fileContent = null;

        StringBuffer fileContentStr = new StringBuffer("");
        BufferedReader reader = null;
        try {
            // initialize buffered reader
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            // read lines of file
            while ((line = reader.readLine()) != null) {
                // append line to string buffer
                fileContentStr.append(line).append("\n");
            }
            // convert string to byte array
            fileContent = fileContentStr.toString().trim().getBytes();
        } catch (IOException e) {
            throw new IOException("Unable to convert file to byte array. " +
                    e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return fileContent;
    }
}
