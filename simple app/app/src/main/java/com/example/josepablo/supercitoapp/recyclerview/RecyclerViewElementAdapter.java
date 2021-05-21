package com.example.josepablo.supercitoapp.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.josepablo.supercitoapp.R;
import com.example.josepablo.supercitoapp.object.Elemento;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 9/6/2017.
 */

public class RecyclerViewElementAdapter extends RecyclerView.Adapter<RecyclerViewElementAdapter.CustomViewHolder> {

    // TODO: (1) DECLARAR ESTRUCUTRA DE DATOS
    private ArrayList<Elemento> elementos;

    // TODO: 11.- Creamos un miembro derivado de la interfaz creada
    private RecycleViewClickListener listener;

    // TODO: (2) CONSTRUCTOR
    public RecyclerViewElementAdapter(ArrayList<Elemento> elementos, RecycleViewClickListener listener){
        this.elementos = elementos;
        this.listener = listener;
    }

    //TODO: (3) DEFINIMOS EL PATRON VIEWHOLDER CREANDO UNA CLASE ESTÁTICA PARA DEFINIR ELEMENTOS
    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvElemento;
        private TextView tvPrecioElementos;
        private TextView tvNumeroElementos;

        CustomViewHolder(View vista){
            super(vista);

            tvElemento = (TextView) vista.findViewById(R.id.tvElemento);
            tvPrecioElementos = (TextView) vista.findViewById(R.id.tvPrecioElementos);
            tvNumeroElementos = (TextView) vista.findViewById(R.id.tvNumeroElementos);
        }
    }

    //TODO: (4) SE IMPLEMENTAN MÉTODOS REQUERIDOS
    @Override
    public int getItemCount(){
        return elementos.size();
    }

    //TODO: (5) EL MÉTODO SIRVE PARA COLOCAR VALORES
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position){

        float total = elementos.get(position).getPrecioProducto() * elementos.get(position).getCantidad();
        String precio = "$ " + String.format("%.2f", total);
        String cantidad = elementos.get(position).getCantidad() + " de " + "$" + String.format("%.2f", elementos.get(position).getPrecioProducto());

        holder.tvElemento.setText(elementos.get(position).getNombreProducto());
        holder.tvPrecioElementos.setText(precio);
        holder.tvNumeroElementos.setText(cantidad);
    }

    // TODO (6) SE USA
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_compra, parent, false);

        // TODO: 13.- Agregamos un objeto RowViewProductHolder y eliminamos las dos líneas anteriores
        return new RowViewElementHolder(vista, listener);

    }
}
