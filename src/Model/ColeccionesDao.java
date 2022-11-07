package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Controller.gestionarConexion;

public class ColeccionesDao {

    public ArrayList<Colecciones> getListaColecciones() {
        ArrayList<Colecciones> listaColecciones = new ArrayList<Colecciones>();

        try {
            String consulta = "SELECT * FROM colecciones";
            PreparedStatement sentencia = gestionarConexion.getConexion().prepareStatement(consulta);
            ResultSet tablas = gestionarConexion.getConexion().getMetaData().getTables(null, null, "comics", null);
            if (tablas.next()) {
                ResultSet res = sentencia.executeQuery(consulta);
                while (res.next()) {
                    Colecciones col = new Colecciones(res.getInt(1), res.getString(2));
                    listaColecciones.add(col);
                }
                res.close();
            }

            sentencia.close();
            return listaColecciones;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar las Colecciones");
            return null;
        }
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
            return col;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
