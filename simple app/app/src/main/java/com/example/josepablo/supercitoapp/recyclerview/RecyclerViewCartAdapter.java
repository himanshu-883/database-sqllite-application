package com.example.josepablo.supercitoapp.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.josepablo.supercitoapp.R;
import com.example.josepablo.supercitoapp.object.Carrito;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 9/6/2017.
 */

public class RecyclerViewCartAdapter extends RecyclerView.Adapter<RecyclerViewCartAdapter.CustomViewHolder> {

    // TODO: (1) DECLARAR ESTRUCUTRA DE DATOS
    private ArrayList<Carrito> carritos;

    // TODO: 11.- Creamos un miembro derivado de la interfaz creada
    private RecycleViewClickListener listener;

    // TODO: (2) CONSTRUCTOR
    public RecyclerViewCartAdapter(ArrayList<Carrito> productos, RecycleViewClickListener listener){
        this.carritos = productos;
        this.listener = listener;
    }

    //TODO: (3) DEFINIMOS EL PATRON VIEWHOLDER CREANDO UNA CLASE ESTÁTICA PARA DEFINIR ELEMENTOS
    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCarritoNombre;
        private TextView tvCarritoPrecio;
        private TextView tvCarritoElementos;

        CustomViewHolder(View vista){
            super(vista);

            tvCarritoNombre = (TextView) vista.findViewById(R.id.tvCarritoNombre);
            tvCarritoPrecio = (TextView) vista.findViewById(R.id.tvCarritoPrecio);
            tvCarritoElementos = (TextView) vista.findViewById(R.id.tvCarritoElementos);
        }
    }

    //TODO: (4) SE IMPLEMENTAN MÉTODOS REQUERIDOS
    @Override
    public int getItemCount(){
        return carritos.size();
    }

    //TODO: (5) EL MÉTODO SIRVE PARA COLOCAR VALORES
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position){

        String precio = "$ " + String.format("%.2f", carritos.get(position).getTotal());
        String noElementos = carritos.get(position).getElementos() + " elementos";
        String nombre = "Compra de " + carritos.get(position).getFecha();

        holder.tvCarritoNombre.setText(nombre);
        holder.tvCarritoPrecio.setText(precio);
        holder.tvCarritoElementos.setText(noElementos);
    }

    // TODO (6) SE USA
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_carrito, parent, false);

        // TODO: 13.- Agregamos un objeto RowViewProductHolder y eliminamos las dos líneas anteriores
        return new RowViewCartHolder(vista, listener);

    }
}
