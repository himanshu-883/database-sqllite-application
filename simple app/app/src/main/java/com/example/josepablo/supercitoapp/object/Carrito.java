package com.example.josepablo.supercitoapp.object;

/**
 * Created by Jose Pablo on 10/15/2017.
 */

public class Carrito {
    private int id;
    private String fecha;
    private float total;
    private int elementos;

    public Carrito(int id, String fecha, float total, int elementos) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.elementos = elementos;
    }

    public Carrito(String fecha, float total, int elementos) {
        this.fecha = fecha;
        this.total = total;
        this.elementos = elementos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getElementos() {
        return elementos;
    }

    public void setElementos(int elementos) {
        this.elementos = elementos;
    }
}
