package com.example.josepablo.supercitoapp.object;

/**
 * Created by Jose Pablo on 10/15/2017.
 */

public class Elemento {
    private int idProducto;
    private String nombreProducto;
    private float precioProducto;
    private int cantidad;

    public Elemento(int idProducto, String nombreProducto, float precioProducto, int cantidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidad = cantidad;
    }

    public Elemento(String nombreProducto, float precioProducto, int cantidad) {
        this.idProducto = -1;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(float precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
