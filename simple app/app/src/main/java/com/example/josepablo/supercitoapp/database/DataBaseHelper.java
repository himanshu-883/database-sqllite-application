package com.example.josepablo.supercitoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.josepablo.supercitoapp.database.carrito.CarritosContract;
import com.example.josepablo.supercitoapp.database.elemento.ElementosContract;
import com.example.josepablo.supercitoapp.database.producto.ProductosContract;

/**
 * Created by Jose Pablo on 10/16/2017.
 */

// TODO: 3.- Creamos clase que extiende a SQLiteOpenHelper
public class DataBaseHelper extends SQLiteOpenHelper {

    // TODO: 4.- declaramos variables para nombre y versión de DB
    private static final String DB_NOMBRE = "supercito.db";
    private static final int DB_VERSION = 2;


    // TODO: 5.- Creación de sentencia SQL para crear tabla
    public static final String CREATE_PRODUCTOS_TABLE = "CREATE TABLE "
            + ProductosContract.Entrada.NOMBRE_TABLA + "("
            + ProductosContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProductosContract.Entrada.COLUMNA_NOMBRE + " TEXT,"
            + ProductosContract.Entrada.COLUMNA_PRECIO + " REAL,"
            + ProductosContract.Entrada.COLUMNA_IMGURL + " TEXT" + ") ";

    public static final String CREATE_CARRITOS_TABLE = "CREATE TABLE "
            + CarritosContract.Entrada.NOMBRE_TABLA + "("
            + CarritosContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CarritosContract.Entrada.COLUMNA_FECHA + " TEXT,"
            + CarritosContract.Entrada.COLUMNA_TOTAL + " REAL,"
            + CarritosContract.Entrada.COLUMNA_ELEMENTOS + " INTEGER" + ") ";

    public static final String CREATE_ELEMENTOS_TABLE = "CREATE TABLE "
            + ElementosContract.Entrada.NOMBRE_TABLA + "("
            + ElementosContract.Entrada.COLUMNA_CARRITO_ID + " INTEGER, "
            + ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO + " TEXT, "
            + ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO + " REAL, "
            + ElementosContract.Entrada.COLUMNA_CANTIDAD + " INTEGER,"
            + "FOREIGN KEY (" + ElementosContract.Entrada.COLUMNA_CARRITO_ID + ") "
            + "REFERENCES " + CarritosContract.Entrada.NOMBRE_TABLA + "(" + CarritosContract.Entrada.COLUMNA_ID + ")) ";

    // TODO: 6.- Creación de sentencia SQL para eliminar tabla
    private static final String SQL_DELETE_ENTRIES_PRODUCTOS =
            "DROP TABLE IF EXISTS " + ProductosContract.Entrada.NOMBRE_TABLA;

    private static final String SQL_DELETE_ENTRIES_ELEMENTOS =
            "DROP TABLE IF EXISTS " + ElementosContract.Entrada.NOMBRE_TABLA;

    private static final String SQL_DELETE_ENTRIES_CARRITOS =
            "DROP TABLE IF EXISTS " + ElementosContract.Entrada.NOMBRE_TABLA;

    // TODO: 7.- Constructor
    public DataBaseHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    // TODO: 8.- Para mandar a crear las tablas
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PRODUCTOS_TABLE);
        sqLiteDatabase.execSQL(CREATE_CARRITOS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ELEMENTOS_TABLE);
    }

    // TODO: 9.- Para actualizar las tablas cuando cambie de versión la DB
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_PRODUCTOS);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_CARRITOS);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_ELEMENTOS);
        onCreate(sqLiteDatabase);
    }
}
