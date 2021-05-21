package com.example.josepablo.supercitoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.supercitoapp.database.producto.ProductoCRUD;
import com.example.josepablo.supercitoapp.object.Producto;

public class ProductDetailActivity extends AppCompatActivity {

    private static int prodId = -1;
    private ProductoCRUD producto;
    private Producto prod;

    private boolean deleted = false;

    private TextView tvDetalleNombre;
    private TextView tvDetallePrecio;

    private Button bEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        prodId = intent.getIntExtra(MainActivity.PROD_ID, -1);

        tvDetalleNombre = (TextView) findViewById(R.id.tvDetalleNombre);
        tvDetallePrecio = (TextView) findViewById(R.id.tvDetallePrecio);

        bEliminar = (Button) findViewById(R.id.bEliminar);

        if(prodId != -1){
            producto = new ProductoCRUD(this);

            prod = producto.getProducto(prodId);

            if(prod != null){
                toolbar.setTitle("Supercito");

                tvDetalleNombre.setText(prod.getNombre());
                tvDetallePrecio.setText(String.format("%.2f", prod.getPrecio()));

                bEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog dialBox = AskOption();
                        dialBox.show();
                    }
                });


                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProductDetailActivity.this, AddProductActivity.class);
                        intent.putExtra(MainActivity.UPDATE_PROD, true);
                        intent.putExtra(MainActivity.PROD_ID, prod.getId());
                        startActivity(intent);
                    }
                });

            }
        }


    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Borrar Producto")
                .setMessage("¿En verdad desea eliminar el producto?")
                .setIcon(R.drawable.ic_error_outline_black_24dp)

                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        producto.deleteProducto(prod);
                        dialog.dismiss();
                        Toast.makeText(ProductDetailActivity.this, "Producto Eliminado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }

                })



                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
