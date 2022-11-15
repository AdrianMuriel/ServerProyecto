package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//········ PAQUETES ···························································
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.ArrayList;

import Model.Colecciones;
import Model.ColeccionesDao;
//········ CLASES ·····························································
import Model.Comics;
import Model.ComicsDao;

public class gestionarConsultas extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarConsultas(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        File pdf;
        FileInputStream fis;
        File repPath = new File("./reports");
        String comando;
        try {
            comando = (String) in.readObject();
            while (!comando.equalsIgnoreCase("fin")) {
                int bytes = 0;
                byte[] buffer = new byte[4 * 1024];
                File file;
                FileOutputStream fos;
                long size;

                String accion = comando.split("··")[0];

                ComicsDao comDao = new ComicsDao();
                ColeccionesDao colDao = new ColeccionesDao();
                switch (accion) {
                    case "startConnection":
                        gestionarConexion.conectar();
                        break;
                    case "endConnection":
                        gestionarConexion.cerrarConexion();
                        break;
                    case "getConnection":
                        Connection con = gestionarConexion.getConexion();
                        out.writeObject(con);
                        break;
                    case "getConData":
                        String[] dataCon = gestionarConexion.leerProperties();
                        out.writeObject(dataCon);
                        break;
                    case "SetConData":
                        String[] conData = (String[]) in.readObject();
                        gestionarConexion.cambiarProperties(conData);
                        break;
                    case "informeColeccion":
                        gestionarReportes.getColeccionReporte(comando.split("··")[1]);
                        pdf = new File(repPath.getAbsolutePath() + "/libreria_coleccion.pdf");
                        bytes = 0;
                        fis = new FileInputStream(pdf);
                        out.writeLong(pdf.length());
                        buffer = new byte[4 * 1024];
                        while ((bytes = fis.read(buffer)) != -1) {
                            out.write(buffer, 0, bytes);
                            out.flush();
                        }
                        break;
                    case "informeNombre":
                        gestionarReportes.getNombreReporte(comando.split("··")[1]);
                        pdf = new File(repPath.getAbsolutePath() + "/libreria_nombre.pdf");
                        bytes = 0;
                        fis = new FileInputStream(pdf);
                        out.writeLong(pdf.length());
                        buffer = new byte[4 * 1024];
                        while ((bytes = fis.read(buffer)) != -1) {
                            out.write(buffer, 0, bytes);
                            out.flush();
                        }
                        break;
                    case "ListaComics":
                        ArrayList<Comics> listaComics = comDao.getListaComics();
                        out.writeObject(listaComics);
                        break;
                    case "ListaColecciones":
                        ArrayList<Colecciones> listaColecciones = colDao.getListaColecciones();
                        out.writeObject(listaColecciones);
                        break;
                    case "ObtenerColeccion":
                        Colecciones col = colDao.getColeccion(Integer.valueOf(comando.split("··")[1]));
                        out.writeObject(col);
                        break;
                    case "ObtenerComic":
                        int existe = comDao.getComic(comando.split("··")[1]);
                        out.writeObject(existe);
                        break;
                    case "EliminarComic":
                        comDao.removeComic(comando.split("··")[1], Integer.valueOf(comando.split("··")[2]));
                        out.writeObject(1);
                        break;
                    case "BuscarComic":
                        Comics c = comDao.searchComic(comando.split("··")[1]);
                        out.writeObject(c);
                        break;
                    case "InsertarComic":
                        File fi = new File("imagen.jpg");
                        file = new File(fi.getAbsolutePath());
                        fos = new FileOutputStream(fi.getAbsolutePath());
                        
                        size = in.readLong();
                        while (size > 0
                                && (bytes = in.read(
                                        buffer, 0,
                                        (int) Math.min(buffer.length, size))) != -1) {
                            fos.write(buffer, 0, bytes);
                            size -= bytes;
                        }
                        fos.close();

                        comDao.addComic(
                                comando.split("··")[1],
                                Integer.valueOf(comando.split("··")[2]),
                                Float.valueOf(comando.split("··")[3]),
                                Integer.valueOf(comando.split("··")[4]),
                                java.sql.Date.valueOf(comando.split("··")[5]),
                                comando.split("··")[6],
                                file);

                        out.writeObject(1);

                        break;
                    case "ModificarComic":
                        File fil = new File("imagen.jpg");
                        file = new File(fil.getAbsolutePath());
                        fos = new FileOutputStream("./imagen.jpg");
                        
                        size = in.readLong();
                        while (size > 0
                                && (bytes = in.read(
                                        buffer, 0,
                                        (int) Math.min(buffer.length, size))) != -1) {
                            fos.write(buffer, 0, bytes);
                            size -= bytes;
                        }
                        fos.close();
                        comDao.updateComic(
                                comando.split("··")[1],
                                Integer.valueOf(comando.split("··")[2]),
                                Float.valueOf(comando.split("··")[3]),
                                Integer.valueOf(comando.split("··")[4]),
                                file,
                                java.sql.Date.valueOf(comando.split("··")[5]),
                                comando.split("··")[6]);

                        out.writeObject(1);
                        break;
                    case "ModificarComicNoImage":
                        comDao.updateComicNoImage(
                                comando.split("··")[1],
                                Integer.valueOf(comando.split("··")[2]),
                                Float.valueOf(comando.split("··")[3]),
                                Integer.valueOf(comando.split("··")[4]),
                                java.sql.Date.valueOf(comando.split("··")[5]),
                                comando.split("··")[6]);
                        out.writeObject(1);
                        break;
                    default:
                        out.writeObject("no");
                        break;
                }
                comando = (String) in.readObject();
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}