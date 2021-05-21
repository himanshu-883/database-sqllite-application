package com.example.josepablo.supercitoapp.database.carrito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.josepablo.supercitoapp.database.DataBaseHelper;
import com.example.josepablo.supercitoapp.database.elemento.ElementosContract;
import com.example.josepablo.supercitoapp.object.Carrito;
import com.example.josepablo.supercitoapp.object.Elemento;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 10/17/2017.
 */

public class CarritoCRUD {
    private DataBaseHelper helper;

    public CarritoCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public long newCarrito(Carrito item){
        // TODO: 11.- Solicitamos la base de datos en modo escritura
        // Obtiene la DB en modo de escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // TODO: 12.- Mapeamos columnas con valores
        // Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre de columna
        ContentValues values = new ContentValues();
        //values.put(CarritosContract.Entrada.COLUMNA_ID, carrito.getId());
        values.put(CarritosContract.Entrada.COLUMNA_FECHA, item.getFecha());
        values.put(CarritosContract.Entrada.COLUMNA_TOTAL, item.getTotal());
        values.put(CarritosContract.Entrada.COLUMNA_ELEMENTOS, item.getElementos());

        // TODO: 13.- Insertamos fila
        // Inserta la nueva fila, regresando el valor de la primary key
        long newRowId = db.insert(CarritosContract.Entrada.NOMBRE_TABLA, null, values);

        // cierra conexión
        db.close();

        return newRowId;
    }

    public ArrayList<Carrito> getCarritos(){
        // TODO: 14.- Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Carrito> items = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                CarritosContract.Entrada.COLUMNA_ID,
                CarritosContract.Entrada.COLUMNA_FECHA,
                CarritosContract.Entrada.COLUMNA_TOTAL,
                CarritosContract.Entrada.COLUMNA_ELEMENTOS,
        };

        // TODO: 15.- Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor c = db.query(
                CarritosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        // TODO: 16.- Se recorren los resultados y se añaden a la lista
        while (c.moveToNext()){
            items.add(new Carrito(
                    c.getInt(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_FECHA)),
                    c.getFloat(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_TOTAL)),
                    c.getInt(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_ELEMENTOS))
            ));
        }
        // TODO: 17.- Cerramos conexión y regresamos elementos
        c.close();
        return items;
    }

    public Carrito getCarrito(int id){
        Carrito item = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                CarritosContract.Entrada.COLUMNA_ID,
                CarritosContract.Entrada.COLUMNA_FECHA,
                CarritosContract.Entrada.COLUMNA_TOTAL,
                CarritosContract.Entrada.COLUMNA_ELEMENTOS,
        };

        // TODO: 18.- usamos los parámetros para obtener una sentencia "WHERE"
        Cursor c = db.query(
                CarritosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                " id = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (c.moveToNext()){
            item = new Carrito(
                    c.getInt(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_FECHA)),
                    c.getFloat(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_TOTAL)),
                    c.getInt(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_ELEMENTOS))
            );
        }

        c.close();
        return item;
    }

    public Carrito getCarrito(long rowId){
        Carrito item = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                CarritosContract.Entrada.COLUMNA_ID,
                CarritosContract.Entrada.COLUMNA_FECHA,
                CarritosContract.Entrada.COLUMNA_TOTAL,
                CarritosContract.Entrada.COLUMNA_ELEMENTOS,
        };

        // TODO: 18.- usamos los parámetros para obtener una sentencia "WHERE"
        Cursor c = db.query(
                CarritosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                " rowid = ?", //texto para filtrar
                new String[]{String.valueOf(rowId)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (c.moveToNext()){
            item = new Carrito(
                    c.getInt(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_FECHA)),
                    c.getFloat(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_TOTAL)),
                    c.getInt(c.getColumnIndexOrThrow(CarritosContract.Entrada.COLUMNA_ELEMENTOS))
            );
        }

        c.close();
        return item;
    }
}
