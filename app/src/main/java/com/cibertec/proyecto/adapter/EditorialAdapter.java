package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cibertec.proyecto.entity.Editorial;

import java.util.List;

public class EditorialAdapter extends ArrayAdapter<Editorial>  {

    private Context context;
    private List<Editorial> lista;

    public EditorialAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

}
