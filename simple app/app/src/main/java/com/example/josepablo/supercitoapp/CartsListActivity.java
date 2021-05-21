package com.example.josepablo.supercitoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.josepablo.supercitoapp.database.carrito.CarritoCRUD;
import com.example.josepablo.supercitoapp.database.producto.ProductoCRUD;
import com.example.josepablo.supercitoapp.object.Carrito;
import com.example.josepablo.supercitoapp.recyclerview.RecycleViewClickListener;
import com.example.josepablo.supercitoapp.recyclerview.RecyclerViewCartAdapter;
import com.example.josepablo.supercitoapp.recyclerview.RecyclerViewProductAdapter;

import java.util.ArrayList;

public class CartsListActivity extends AppCompatActivity {

    private RecyclerView rvCarrito;
    private LinearLayoutManager linearLayoutManager;
    private ProductoCRUD producto;

    private CarritoCRUD carritoCRUD;

    private ArrayList<Carrito> carritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts_list);

        rvCarrito = (RecyclerView) findViewById(R.id.rvCarritos);

        carritoCRUD = new CarritoCRUD(this);
        carritos = carritoCRUD.getCarritos();

        if(carritos.size() > 0){

            rvCarrito.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(this);
            rvCarrito.setLayoutManager(linearLayoutManager);

            RecyclerViewCartAdapter adapter = new RecyclerViewCartAdapter(carritos, new RecycleViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(CartsListActivity.this, CartDetailActivity.class);
                    intent.putExtra(MainActivity.CARRITO_ID, carritos.get(position).getId());
                    startActivity(intent);
                }
            });
            rvCarrito.setAdapter(adapter);

        }



    }
}
