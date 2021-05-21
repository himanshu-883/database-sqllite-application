package com.example.josepablo.supercitoapp.database.producto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.josepablo.supercitoapp.database.DataBaseHelper;
import com.example.josepablo.supercitoapp.object.Producto;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 10/16/2017.
 */

public class ProductoCRUD {

    private DataBaseHelper helper;

    public ProductoCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void newProducto(Producto item){
        // TODO: 11.- Solicitamos la base de datos en modo escritura
        // Obtiene la DB en modo de escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // TODO: 12.- Mapeamos columnas con valores
        // Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre de columna
        ContentValues values = new ContentValues();
        values.put(ProductosContract.Entrada.COLUMNA_NOMBRE, item.getNombre());
        values.put(ProductosContract.Entrada.COLUMNA_PRECIO, item.getPrecio());
        values.put(ProductosContract.Entrada.COLUMNA_IMGURL, item.getImgUrl());

        // TODO: 13.- Insertamos fila
        // Inserta la nueva fila, regresando el valor de la primary key
        long newRowId = db.insert(ProductosContract.Entrada.NOMBRE_TABLA, null, values);

        // cierra conexión
        db.close();
    }

    public ArrayList<Producto> getProductos(){
        // TODO: 14.- Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Producto> items = new ArrayList<Producto>();

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                ProductosContract.Entrada.COLUMNA_ID,
                ProductosContract.Entrada.COLUMNA_NOMBRE,
                ProductosContract.Entrada.COLUMNA_PRECIO,
                ProductosContract.Entrada.COLUMNA_IMGURL,
        };

        // TODO: 15.- Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor c = db.query(
                ProductosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        // TODO: 16.- Se recorren los resultados y se añaden a la lista
        while (c.moveToNext()){
            items.add(new Producto(
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_NOMBRE)),
                    c.getFloat(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_PRECIO)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_IMGURL))
            ));
        }
        // TODO: 17.- Cerramos conexión y regresamos elementos
        c.close();
        return items;
    }

    public Producto getProducto(int id){
        Producto item = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        // Especificamos las columnas a usar
        String[] columnas = {
                ProductosContract.Entrada.COLUMNA_ID,
                ProductosContract.Entrada.COLUMNA_NOMBRE,
                ProductosContract.Entrada.COLUMNA_PRECIO,
                ProductosContract.Entrada.COLUMNA_IMGURL,
        };

        // TODO: 18.- usamos los parámetros para obtener una sentencia "WHERE"
        Cursor c = db.query(
                ProductosContract.Entrada.NOMBRE_TABLA, // nombre tabla
                columnas, // columnas
                " id = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (c.moveToNext()){
            item = new Producto(
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_NOMBRE)),
                    c.getFloat(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_PRECIO)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Entrada.COLUMNA_IMGURL))
            );
        }

        c.close();
        return item;
    }

    public void updateProducto(Producto item){
        // Obtiene la DB en modo de escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre de columna
        ContentValues values = new ContentValues();
        //values.put(ProductosContract.Entrada.COLUMNA_ID, item.getId());
        values.put(ProductosContract.Entrada.COLUMNA_NOMBRE, item.getNombre());
        values.put(ProductosContract.Entrada.COLUMNA_PRECIO, item.getPrecio());
        values.put(ProductosContract.Entrada.COLUMNA_IMGURL, item.getImgUrl());

        // TODO: 19.- Actualizamos fila
        // Inserta la nueva fila, regresando el valor de la primary key
        db.update(
                ProductosContract.Entrada.NOMBRE_TABLA,
                values,
                "id = ?",
                new String[]{String.valueOf(item.getId())}
        );

        // cierra conexión
        db.close();
    }

    public void deleteProducto(Producto item){
        // Obtiene la DB en modo de escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // TODO: 20.- Eliminamos fila
        db.delete(
                ProductosContract.Entrada.NOMBRE_TABLA,
                "id = ?",
                new String[]{String.valueOf(item.getId())}
        );

        // cierra conexión
        db.close();
    }

}
