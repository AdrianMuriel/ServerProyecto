package Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.*;

public class gestionarComics {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarComics(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Comics> listarComics() {
        try {
            out.writeObject("ListaComics");
            ArrayList<Comics> listaComics = (ArrayList<Comics>) in.readObject();
            return listaComics;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeComic(Comics c) {
        try {
            out.writeObject("EliminarComic-" + c.getTitulo() + "-" + c.getNum_col());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
