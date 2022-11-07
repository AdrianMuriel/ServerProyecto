package Model;

import java.io.Serializable;
import java.sql.*;

import javax.sql.rowset.serial.SerialBlob;

public class Comics implements Serializable {
    int num_col, cantidad;
    String titulo;
    String estado;
    Float precio;
    Date fecha;
    Blob imagen;

    public Comics() {
    }

    public Comics(int num_col, String titulo, Blob imagen, Date fecha, int cantidad, Float precio, String estado) {
        try {
            this.num_col = num_col;
            this.cantidad = cantidad;
            this.titulo = titulo;
            this.estado = estado;
            this.precio = precio;
            this.fecha = fecha;
            this.imagen = new SerialBlob(imagen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Comics(int num_col, String titulo, Date fecha, int cantidad, Float precio, String estado) {
        this.num_col = num_col;
        this.cantidad = cantidad;
        this.titulo = titulo;
        this.estado = estado;
        this.precio = precio;
        this.fecha = fecha;
    }

    public int getNum_col() {
        return num_col;
    }

    public void setNum_col(int num_col) {
        this.num_col = num_col;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getPrecio() {
        return precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return titulo;
    }

}
