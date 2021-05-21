package com.example.josepablo.supercitoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.josepablo.supercitoapp.database.carrito.CarritoCRUD;
import com.example.josepablo.supercitoapp.database.elemento.ElementoCRUD;
import com.example.josepablo.supercitoapp.object.Carrito;
import com.example.josepablo.supercitoapp.object.Elemento;
import com.example.josepablo.supercitoapp.recyclerview.RecycleViewClickListener;
import com.example.josepablo.supercitoapp.recyclerview.RecyclerViewElementAdapter;

import java.util.ArrayList;

public class CartDetailActivity extends AppCompatActivity {

    private RecyclerView rvElementosDetail;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<Elemento> elementos;
    private Carrito carrito;

    private CarritoCRUD carritoCRUD;
    private ElementoCRUD elementoCRUD;

    private CollapsingToolbarLayout toolbarLayout;

    private int carritoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        carritoId = intent.getIntExtra(MainActivity.CARRITO_ID, -1);

        if(carritoId != -1){

            carritoCRUD = new CarritoCRUD(this);
            elementoCRUD = new ElementoCRUD(this);

            carrito = carritoCRUD.getCarrito(carritoId);
            if(carrito != null){

                toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
                toolbarLayout.setTitle(carrito.getFecha());

                elementos = elementoCRUD.getElementos(carrito.getId());

                if(elementos.size() > 0){

                    rvElementosDetail = (RecyclerView) findViewById(R.id.rvElementosDetail);

                    rvElementosDetail.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(this);
                    rvElementosDetail.setLayoutManager(linearLayoutManager);

                    RecyclerViewElementAdapter adapter = new RecyclerViewElementAdapter(elementos, new RecycleViewClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }
                    });
                    rvElementosDetail.setAdapter(adapter);

                }
            }


        }


    }


}
