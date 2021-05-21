package com.example.josepablo.supercitoapp.database.elemento;

import android.provider.BaseColumns;

/**
 * Created by Jose Pablo on 10/17/2017.
 */

public final class ElementosContract {
    private ElementosContract(){};

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "elementos";
        public static final String COLUMNA_CARRITO_ID = "carrito_id";
        public static final String COLUMNA_NOMBRE_PRODUCTO = "nombre_producto";
        public static final String COLUMNA_PRECIO_PRODUCTO = "precio_producto";
        public static final String COLUMNA_CANTIDAD = "cantidad";
    }
}
