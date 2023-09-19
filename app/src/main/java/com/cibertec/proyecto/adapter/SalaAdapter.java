package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cibertec.proyecto.entity.Sala;


import java.util.List;

public class SalaAdapter extends ArrayAdapter<Sala>  {

    private Context context;
    private List<Sala> lista;

    public SalaAdapter(@NonNull Context context, int resource, @NonNull List<Sala> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

}
