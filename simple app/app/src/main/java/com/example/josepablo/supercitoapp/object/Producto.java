package com.example.josepablo.supercitoapp.object;

/**
 * Created by Jose Pablo on 10/15/2017.
 */

public class Producto {
    private int id;
    private String nombre;
    private float precio;
    private String imgUrl;

    public Producto(String nombre, float precio, String imgUrl){
        this.nombre = nombre;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    public Producto(int id, String nombre, float precio, String imgUrl){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
