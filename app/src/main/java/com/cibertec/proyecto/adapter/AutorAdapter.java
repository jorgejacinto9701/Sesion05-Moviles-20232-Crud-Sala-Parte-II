package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;

import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_autor_consulta_item, parent, false);

        Autor objProducto = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtID);
        txtID.setText(String.valueOf(objProducto.getIdAutor()));

        TextView txtNombre = row.findViewById(R.id.txtNombre);
        txtNombre.setText(objProducto.getNombres());

        TextView txtApellido = row.findViewById(R.id.txtApellido);
        txtApellido.setText(objProducto.getApellidos());

        TextView txtCorreo = row.findViewById(R.id.txtCorreo);
        txtCorreo.setText(objProducto.getCorreo());

        TextView txtFechaNac = row.findViewById(R.id.txtFechaNac);
        txtFechaNac.setText(objProducto.getFechaNacimiento());

        TextView txtCelular = row.findViewById(R.id.txtCelular);
        txtCelular.setText(objProducto.getTelefono());

        return row;
    }



}
