package com.example.josepablo.supercitoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.josepablo.supercitoapp.database.producto.ProductoCRUD;
import com.example.josepablo.supercitoapp.object.Producto;
import com.example.josepablo.supercitoapp.recyclerview.RecycleViewClickListener;
import com.example.josepablo.supercitoapp.recyclerview.RecyclerViewProductAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String UPDATE_PROD = "com.example.josepablo.supercitoapp.isUpdate";
    public static final String PROD_ID = "com.example.josepablo.supercitoapp.productoId";
    public static final String CARRITO_ID = "com.example.josepablo.supercitoapp.carritoId";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProductoCRUD producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        producto = new ProductoCRUD(this);

        final ArrayList<Producto> productos = producto.getProductos();

        if(productos.size() > 0) {

            recyclerView = (RecyclerView) findViewById(R.id.rvProductos);
            recyclerView.setHasFixedSize(true);


            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);



            // TODO: 14.- Ingresamoes a nuestro adaptador un nuevo listenr para poder saber el elemento al que se le dio click
            RecyclerViewProductAdapter adapter = new RecyclerViewProductAdapter(productos, new RecycleViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                    //intent.putExtra(UPDATE_PROD, true);
                    intent.putExtra(PROD_ID, productos.get(position).getId());
                    //insertar Parcelable del producto
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_add:
                intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_carts:
                intent = new Intent(MainActivity.this, CartsListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
