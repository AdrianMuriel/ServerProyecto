package Controller;

import java.io.File;
import java.io.FileInputStream;
//········ PAQUETES ···························································
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        String comando;
        try {
            comando = (String) in.readObject();
            while (!comando.equalsIgnoreCase("fin")) {
                if (comando.equalsIgnoreCase("ListaComics")) {
                    ComicsDao comDao = new ComicsDao();
                    ArrayList<Comics> listaComics = comDao.getListaComics();
                    out.writeObject(listaComics);
                } else if (comando.split("··")[0].equalsIgnoreCase("ListaColecciones")) {
                    ColeccionesDao colDao = new ColeccionesDao();
                    ArrayList<Colecciones> listaColecciones = colDao.getListaColecciones();
                    out.writeObject(listaColecciones);
                } else if (comando.split("··")[0].equalsIgnoreCase("ObtenerColeccion")) {
                    ColeccionesDao colDao = new ColeccionesDao();
                    Colecciones col = colDao.getColeccion(Integer.valueOf(comando.split("··")[1]));
                    out.writeObject(col);
                } else if (comando.split("··")[0].equalsIgnoreCase("EliminarComic")) {
                    ComicsDao comDao = new ComicsDao();
                    comDao.removeComic(comando.split("··")[1], Integer.valueOf(comando.split("·")[2]));
                    out.writeObject(1);
                } else if (comando.split("··")[0].equalsIgnoreCase("BuscarComic")) {
                    ComicsDao comDao = new ComicsDao();
                    Comics c = comDao.searchComic(comando.split("··")[1]);
                    out.writeObject(c);
                } else if (comando.split("··")[0].equalsIgnoreCase("InsertarComic")) {
                    ComicsDao comDao = new ComicsDao();
                    File img = new File(comando.split("··")[5]);
                    FileInputStream fis = new FileInputStream(img);
                    comDao.addComic(
                            comando.split("··")[1],
                            Integer.valueOf(comando.split("··")[2]),
                            Float.valueOf(comando.split("··")[3]),
                            Integer.valueOf(comando.split("··")[4]),
                            fis,
                            img,
                            java.sql.Date.valueOf(comando.split("··")[6]),
                            comando.split("··")[7]);
                    out.writeObject(1);
                } else if (comando.split("··")[0].equalsIgnoreCase("ModificarComic")) {
                    ComicsDao comDao = new ComicsDao();
                    File img = new File(comando.split("··")[5]);
                    FileInputStream fis = new FileInputStream(img);
                    comDao.updateComic(
                            comando.split("··")[1],
                            Integer.valueOf(comando.split("··")[2]),
                            Float.valueOf(comando.split("··")[3]),
                            Integer.valueOf(comando.split("··")[4]),
                            fis,
                            img,
                            java.sql.Date.valueOf(comando.split("··")[6]),
                            comando.split("··")[7]);
                    out.writeObject(1);
                } else {
                    out.writeObject("no");
                }
                comando = (String) in.readObject();
            }
        } catch (Exception e) {
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