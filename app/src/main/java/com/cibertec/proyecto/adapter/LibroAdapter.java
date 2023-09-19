package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

import com.cibertec.proyecto.entity.Libro;
import java.util.List;

public class LibroAdapter extends ArrayAdapter<Libro>  {

    private Context context;
    private List<Libro> lista;

    public LibroAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

}
