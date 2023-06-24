package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;

import java.util.List;

public class LibroCrudAdapter extends ArrayAdapter<Libro> {
    private Context context;
    private List<Libro> lista;

    public LibroCrudAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista){
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = ( LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_libro_crud_item, parent, false);
        TextView txtIdLibro = row.findViewById(R.id.txtIdLibro);
        TextView txtTituloLibro = row.findViewById(R.id.txtTituloLibro);
        TextView txtAnioLibro = row.findViewById(R.id.txtAnioLibro);
        TextView txtidCategoria = row.findViewById(R.id.txtidCategoria);

        Libro lib = lista.get(position);
        txtIdLibro.setText(String.valueOf(lib.getIdLibro()));
        txtTituloLibro.setText(lib.getTitulo());
        txtAnioLibro.setText(String.valueOf(lib.getAnio()));
        txtidCategoria.setText(lib.getCategoria().getDescripcion());





        return row;
    }

}
