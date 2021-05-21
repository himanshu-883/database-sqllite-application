package com.example.josepablo.supercitoapp.database.elemento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.josepablo.supercitoapp.database.DataBaseHelper;
import com.example.josepablo.supercitoapp.object.Carrito;
import com.example.josepablo.supercitoapp.object.Elemento;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 10/17/2017.
 */

public class ElementoCRUD {
    private DataBaseHelper helper;

    public ElementoCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void newElemento(Elemento item, Carrito carrito){
        // TODO: 11.- Solicitamos la base de datos en modo escritura
        // Obtiene la DB en modo de escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // TODO: 12.- Mapeamos columnas con valores
        // Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre de columna
        ContentValues values = new ContentValues();
        values.put(ElementosContract.Entrada.COLUMNA_CARRITO_ID, carrito.getId());
        values.put(ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO, item.getNombreProducto());
        values.put(ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO, item.getPrecioProducto());
        values.put(ElementosContract.Entrada.COLUMNA_CANTIDAD, item.getCantidad());

        // TODO: 13.- Insertamos fila
        // Inserta la nueva fila, regresando el valor de la primary key
        long newRowId = db.insert(ElementosContract.Entrada.NOMBRE_TABLA, null, values);

        // cierra conexión
        db.close();
    }

    public ArrayList<Elemento> getElementos(){
        // TODO: 14.- Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Elemento> items = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                ElementosContract.Entrada.COLUMNA_CARRITO_ID,
                ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO,
                ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO,
                ElementosContract.Entrada.COLUMNA_CANTIDAD,
        };

        // TODO: 15.- Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor c = db.query(
                ElementosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        // TODO: 16.- Se recorren los resultados y se añaden a la lista
        while (c.moveToNext()){
            items.add(new Elemento(
                    c.getInt(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_CARRITO_ID)),
                    c.getString(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO)),
                    c.getFloat(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO)),
                    c.getInt(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_CANTIDAD))
            ));
        }
        // TODO: 17.- Cerramos conexión y regresamos elementos
        c.close();
        return items;
    }

    public ArrayList<Elemento> getElementos(int id){
        // TODO: 14.- Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Elemento> items = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                ElementosContract.Entrada.COLUMNA_CARRITO_ID,
                ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO,
                ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO,
                ElementosContract.Entrada.COLUMNA_CANTIDAD,
        };

        // TODO: 15.- Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor c = db.query(
                ElementosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                " carrito_id = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        // TODO: 16.- Se recorren los resultados y se añaden a la lista
        while (c.moveToNext()){
            items.add(new Elemento(
                    c.getInt(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_CARRITO_ID)),
                    c.getString(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO)),
                    c.getFloat(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO)),
                    c.getInt(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_CANTIDAD))
            ));
        }
        // TODO: 17.- Cerramos conexión y regresamos elementos
        c.close();
        return items;
    }

    public Elemento getElemento(int id){
        Elemento item = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                ElementosContract.Entrada.COLUMNA_CARRITO_ID,
                ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO,
                ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO,
                ElementosContract.Entrada.COLUMNA_CANTIDAD,
        };

        // TODO: 18.- usamos los parámetros para obtener una sentencia "WHERE"
        Cursor c = db.query(
                ElementosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                " carrito_id = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (c.moveToNext()){
            item = new Elemento(
                    c.getInt(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_CARRITO_ID)),
                    c.getString(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_NOMBRE_PRODUCTO)),
                    c.getFloat(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_PRECIO_PRODUCTO)),
                    c.getInt(c.getColumnIndexOrThrow(ElementosContract.Entrada.COLUMNA_CANTIDAD))
            );
        }

        c.close();
        return item;
    }
}
