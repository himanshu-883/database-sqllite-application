package com.example.josepablo.supercitoapp.recyclerview;

import android.view.View;

/**
 * Created by Jose Pablo on 9/13/2017.
 */

public class RowViewProductHolder extends RecyclerViewProductAdapter.CustomViewHolder implements View.OnClickListener{

    // TODO: 8.- Creamos una nueva clase que extienda a un ViewHolder e implentamos un ClickListeenr

    private RecycleViewClickListener listener;

    public RowViewProductHolder(View itemView, RecycleViewClickListener listener){
        super(itemView);
        this.listener = listener;
        // TODO: 9.- Asignamos a nuestro itemView el evento listener de la clase que está implementando
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        // TODO: 10.- Cuando se de click el evento onClick de nuestra interfaz va a tomar la accion
        listener.onClick(view, getAdapterPosition());
    }

}
