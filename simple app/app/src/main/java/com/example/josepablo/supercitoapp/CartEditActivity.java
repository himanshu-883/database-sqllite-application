package com.example.josepablo.supercitoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.josepablo.supercitoapp.database.carrito.CarritoCRUD;
import com.example.josepablo.supercitoapp.database.elemento.ElementoCRUD;
import com.example.josepablo.supercitoapp.database.producto.ProductoCRUD;
import com.example.josepablo.supercitoapp.object.Carrito;
import com.example.josepablo.supercitoapp.object.Elemento;
import com.example.josepablo.supercitoapp.object.Producto;
import com.example.josepablo.supercitoapp.recyclerview.RecycleViewClickListener;
import com.example.josepablo.supercitoapp.recyclerview.RecyclerViewElementAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartEditActivity extends AppCompatActivity {

    private Carrito carrito;

    private int noElementos = 0;
    private float total = 0F;

    private ArrayList<Elemento> elementos;
    private ArrayList<String> nombres;
    private ArrayList<Producto> productos;

    private Spinner sProductos;
    private FloatingActionButton fabAgregar;
    private RecyclerView rvElementosEdit;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewElementAdapter adapter;
    private EditText etCantidad;

    private ProductoCRUD productoCRUD;

    private ElementoCRUD elementoCRUD;

    private CarritoCRUD carritoCRUD;

    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String fecha = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        sProductos = (Spinner) findViewById(R.id.sProductos);
        fabAgregar = (FloatingActionButton) findViewById(R.id.fabAgregar);
        etCantidad = (EditText) findViewById(R.id.etCantidad);

        rvElementosEdit = (RecyclerView) findViewById(R.id.rvElementosEdit);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(fecha);

        productoCRUD = new ProductoCRUD(this);
        elementoCRUD = new ElementoCRUD(this);
        carritoCRUD =  new CarritoCRUD(this);

        carrito = new Carrito(fecha, 0, 0);

        productos = getProductos();
        elementos = new ArrayList<>();

        if(productos != null){
            nombres = getNombresProductos(productos);

            setSpinnerAdapter(nombres, sProductos);

            fabAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cantidad = etCantidad.getText().toString();
                    if(!cantidad.isEmpty() && Integer.parseInt(cantidad) > 0) {

                        int cantidad1 = Integer.parseInt(cantidad);

                        int position = sProductos.getSelectedItemPosition();
                        Producto curProducto = productos.get(position);

                        Elemento elemento = new Elemento(curProducto.getNombre(), curProducto.getPrecio(), cantidad1);
                        elementos.add(elemento);

                        noElementos++;
                        total += curProducto.getPrecio() * cantidad1;

                        carrito.setElementos(noElementos);
                        carrito.setTotal(total);

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CartEditActivity.this, "Campo de cantidad invalido", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        rvElementosEdit.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        rvElementosEdit.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewElementAdapter(elementos, new RecycleViewClickListener() {
            @Override
            public void onClick(View view, int position) {
            }
        });
        rvElementosEdit.setAdapter(adapter);

        //carrito = new Carrito();
        //carrito.setFecha();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(elementos.size() > 0){
                    if(registerCarrito()){
                        Toast.makeText(CartEditActivity.this, "Compra registrada exitosamene", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CartEditActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });


    }

    private ArrayList<Producto> getProductos(){
        ArrayList<Producto> prod;

        prod = productoCRUD.getProductos();

        if(prod.size() > 0) {
            return prod;
        } else {
            return null;
        }
    }

    private ArrayList<String> getNombresProductos(ArrayList<Producto> productos1){
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < productos1.size(); i++) {
            names.add(productos1.get(i).getNombre());
        }

        return names;
    }

    private void setSpinnerAdapter(ArrayList<String> names, Spinner spinner2){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    private boolean registerCarrito(){
        long rowId;

        rowId = carritoCRUD.newCarrito(carrito);
        if(rowId == -1){
            Toast.makeText(this, "Error al insertar nueva compra.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Carrito curCarrito = carritoCRUD.getCarrito(rowId);

        if(curCarrito == null){
            Toast.makeText(this, "Error al encontrar el carrito", Toast.LENGTH_SHORT).show();
            return false;
        }

        //int carritoId = curCarrito.getId();

        //Insertar elementos con llave del carrito
        for (int i = 0; i < elementos.size(); i++) {
            elementoCRUD.newElemento(elementos.get(i), curCarrito);
        }

        return true;
    }
}
