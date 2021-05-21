package com.example.josepablo.supercitoapp.database.carrito;

import android.provider.BaseColumns;

/**
 * Created by Jose Pablo on 10/17/2017.
 */

public final class CarritosContract {
    private CarritosContract(){};

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "carritos";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_FECHA = "fecha";
        public static final String COLUMNA_TOTAL = "total";
        public static final String COLUMNA_ELEMENTOS = "elementos";
    }
}
