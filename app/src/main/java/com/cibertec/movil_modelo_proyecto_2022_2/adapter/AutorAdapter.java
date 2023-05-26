package com.cibertec.movil_modelo_proyecto_2022_2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;

import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor> {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_item_autor, parent, false);

        TextView txtnombre = row.findViewById(R.id.txtNombreAutor);

        TextView txtcorreo = row.findViewById(R.id.txtCorreoAutor);
        TextView txttelefono = row.findViewById(R.id.txtTelefonoAutor);


        Autor objAutor = lista.get(position);
        txtnombre.setText(objAutor.getNombres());
        txtcorreo.setText(objAutor.getCorreo());
        txttelefono.setText(objAutor.getTelefono());
        return row;


    }

}
