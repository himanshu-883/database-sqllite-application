package com.example.josepablo.supercitoapp.database.producto;

import android.provider.BaseColumns;

/**
 * Created by Jose Pablo on 10/16/2017.
 */

public final class ProductosContract {
    private ProductosContract(){};

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "productos";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_PRECIO = "precio";
        public static final String COLUMNA_IMGURL = "imgUrl";
    }
}
