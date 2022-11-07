package Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Model.Colecciones;
import Model.Comics;

public class gestionarColecciones {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarColecciones(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public Colecciones obtenerColeccion(Comics c) {
        try {
            out.writeObject("ObtenerColeccion-" + c.getNum_col());
            Colecciones col = (Colecciones) in.readObject();
            return col;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
