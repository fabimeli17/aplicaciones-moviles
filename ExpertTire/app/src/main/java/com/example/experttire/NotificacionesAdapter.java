package com.example.experttire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.MyViewHolder> {


    private List<NotificacionBean> notificaciones ;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo, texto, fecha;

        public MyViewHolder(View view) {
            super(view);

            /*
            codigo
            usuario
            estado
            */
            titulo = (TextView) view.findViewById(R.id.titulo);
            texto = (TextView) view.findViewById(R.id.texto);
            fecha = (TextView) view.findViewById(R.id.fecha);

        }

    }

    public NotificacionesAdapter(List<NotificacionBean> notificacionesList) {
        this.notificaciones = notificacionesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificacion_fila, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificacionBean notificacion = notificaciones.get(position);
        holder.titulo.setText(notificacion.getTitulo());
        holder.texto.setText(notificacion.getTexto());
        holder.fecha.setText(notificacion.getFecha());
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }


}
