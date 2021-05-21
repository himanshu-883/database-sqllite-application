package com.example.josepablo.supercitoapp.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josepablo.supercitoapp.R;
import com.example.josepablo.supercitoapp.image.PicassoCircleTransformation;
import com.example.josepablo.supercitoapp.object.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 9/6/2017.
 */

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.CustomViewHolder> {

    // TODO: (1) DECLARAR ESTRUCUTRA DE DATOS
    private ArrayList<Producto> productos;

    // TODO: 11.- Creamos un miembro derivado de la interfaz creada
    private RecycleViewClickListener listener;

    // TODO: (2) CONSTRUCTOR
    public RecyclerViewProductAdapter(ArrayList<Producto> productos, RecycleViewClickListener listener){
        this.productos = productos;
        this.listener = listener;
    }

    //TODO: (3) DEFINIMOS EL PATRON VIEWHOLDER CREANDO UNA CLASE ESTÁTICA PARA DEFINIR ELEMENTOS
    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombre;
        private TextView tvPrecio;
        private ImageView ivFoto;
        private Context myContext;

        CustomViewHolder(View vista){
            super(vista);

            myContext = vista.getContext();
            tvNombre = (TextView) vista.findViewById(R.id.tvNombre);
            tvPrecio = (TextView) vista.findViewById(R.id.tvPrecio);
            ivFoto = (ImageView) vista.findViewById(R.id.ivProducto);
        }
    }

    //TODO: (4) SE IMPLEMENTAN MÉTODOS REQUERIDOS
    @Override
    public int getItemCount(){
        return productos.size();
    }

    //TODO: (5) EL MÉTODO SIRVE PARA COLOCAR VALORES
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position){
        if(!productos.get(position).getImgUrl().equals("null")) {
            //Cargar imagen de url
            Picasso.with(holder.myContext).load(productos.get(position).getImgUrl()).error(R.drawable.ic_error_outline_black_24dp).transform(new PicassoCircleTransformation()).into(holder.ivFoto);
            //holder.ivFoto.setImageResource(productos.get(position).getImagen());
        }
        String price = "$ " + String.format("%.2f", productos.get(position).getPrecio());

        holder.tvNombre.setText(productos.get(position).getNombre());
        holder.tvPrecio.setText(price);
    }

    // TODO (6) SE USA
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_producto, parent, false);

        // TODO: 13.- Agregamos un objeto RowViewProductHolder y eliminamos las dos líneas anteriores
        return new RowViewProductHolder(vista, listener);

    }
}
