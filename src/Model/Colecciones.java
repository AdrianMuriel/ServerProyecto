package Model;

import java.io.Serializable;

public class Colecciones implements Serializable {
    int num_coleccion;
    String titulo;

    public Colecciones() {
    }

    public Colecciones(int num_coleccion, String titulo) {
        this.num_coleccion = num_coleccion;
        this.titulo = titulo;
    }

    public int getNum_coleccion() {
        return num_coleccion;
    }

    public void setNum_coleccion(int num_coleccion) {
        this.num_coleccion = num_coleccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }

}
