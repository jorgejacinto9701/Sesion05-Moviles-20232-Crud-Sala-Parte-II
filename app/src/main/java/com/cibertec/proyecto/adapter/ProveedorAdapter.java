package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.cibertec.proyecto.entity.Proveedor;

import java.util.List;

public class ProveedorAdapter extends ArrayAdapter<Proveedor>  {

    private Context context;
    private List<Proveedor> lista;

    public ProveedorAdapter(@NonNull Context context, int resource, @NonNull List<Proveedor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

}
