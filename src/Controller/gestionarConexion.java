package Controller;

//-------- IMPORTS ------------------------------------------------------------
//-------- PAQUETES -----------------------------------------------------------
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
//-----------------------------------------------------------------------------

public class gestionarConexion {
    private static Connection con;
    private static File conFile = new File(
            gestionarConexion.class.getResource("/connection.properties").getPath());
    private static Properties properties = new Properties();

    /**
     * Este metodo realiza la conexion con la base de datos
     */
    public static void conectar() {
        try {
            System.out.println(conFile.getCanonicalPath());
            properties.load(new FileInputStream(conFile));
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + properties.get("IP") + ":" + properties.get("PORT") + "/"
                    + properties.get("BD");
            con = DriverManager.getConnection(url, properties.get("USER") + "", properties.get("PASSWORD") + "");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha Ocurrido un error al Conectar", "ERROR", 0);
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Ha Ocurrido un error al cargar el Driver", "ERROR", 0);
            e.printStackTrace();
            System.exit(0);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se encuentra el fichero properties", "ERROR", 0);
            System.exit(0);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al leer el fichero properties", "ERROR", 0);
            System.exit(0);
            e.printStackTrace();
        }
    }

    public static Connection getConexion() {
        return con;
    }

    /**
     * Este metodo finaliza la conexion con la base de datos
     */
    public static void cerrarConexion() {
        try {
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha Ocurrido un error al cerrar la conexi�n");
            System.exit(0);
        } // try/catch
    }

    /**
     * Este petodo se usa para modificar el fichero de conexion en el que el usuario
     * modifica: �IP �PUERTO �USUARIO �CONTRASE�A �BASE DE DATOS A USAR
     * 
     * @param datos
     */
    public static void cambiarProperties(String[] datos, String pwd) {
        try {
            properties.load(new FileInputStream(conFile));
            properties.setProperty("IP", datos[0]);
            properties.setProperty("PORT", datos[1]);
            properties.setProperty("BD", datos[2]);
            properties.setProperty("USER", datos[3]);
            properties.setProperty("PASSWORD", pwd.toString());
            FileOutputStream fileOS = new FileOutputStream(conFile);
            properties.store(fileOS, null);
            fileOS.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se encuentra el fichero properties", "ERROR", 0);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al leer el fichero properties", "ERROR", 0);
            e.printStackTrace();
        } // try/catch
    }

    /**
     * Este metodo lee los datos del fichero de conexion y los devuelve
     * 
     * @return
     */
    public static String[] leerProperties() {
        String[] datos = new String[5];
        try {
            properties.load(new FileInputStream(conFile));
            datos[0] = String.valueOf(properties.get("IP"));
            datos[1] = String.valueOf(properties.get("PORT"));
            datos[2] = String.valueOf(properties.get("BD"));
            datos[3] = String.valueOf(properties.get("USER"));
            datos[4] = String.valueOf(properties.get("PASSWORD"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se encuentra el fichero properties", "ERROR", 0);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al leer el fichero properties", "ERROR", 0);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } // try/catch
        return datos;
    }

}
