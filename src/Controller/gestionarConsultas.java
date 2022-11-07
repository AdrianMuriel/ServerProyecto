package Controller;

//-------- PAQUETES -----------------------------------------------------------
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.Colecciones;
import Model.ColeccionesDao;
//-------- CLASES -------------------------------------------------------------
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
                } else if (comando.split("-")[0].equalsIgnoreCase("ObtenerColeccion")) {
                    ColeccionesDao colDao = new ColeccionesDao();
                    Colecciones col = colDao.getColeccion(Integer.valueOf(comando.split("-")[1]));
                    out.writeObject(col);
                } else if (comando.split("-")[0].equalsIgnoreCase("EliminarComic")) {
                    ComicsDao comDao = new ComicsDao();
                    comDao.removeComic(comando.split("-")[1], Integer.valueOf(comando.split("-")[2]));
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