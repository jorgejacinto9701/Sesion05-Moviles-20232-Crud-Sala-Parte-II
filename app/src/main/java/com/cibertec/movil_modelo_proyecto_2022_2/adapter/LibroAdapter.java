package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import java.util.List;

public class LibroAdapter extends ArrayAdapter<Libro>  {

    private Context context;
    private List<Libro> lista;

    public LibroAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    TextView txtTitulo, txtId, txtSerie, txtCategoria, txtPais;
    ImageView img;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_item_libro_consulta, parent, false);

         txtId = row.findViewById(R.id.txtIdLibro);
         txtTitulo = row.findViewById(R.id.txtTituloLibro);
         txtSerie = row.findViewById(R.id.txtSerieLibro);
         txtCategoria = row.findViewById(R.id.txtCategoriaLibro);
         txtPais = row.findViewById(R.id.txtPaisLibro);
         img = row.findViewById(R.id.imgLibro);
        Glide.with(context).load("").into(img);
        Libro objLibro = lista.get(position);
        txtId.setText(String.valueOf(objLibro.getIdLibro()));
        txtTitulo.setText(objLibro.getTitulo());
        txtSerie.setText(objLibro.getSerie());
        txtCategoria.setText(String.valueOf(objLibro.getCategoria().getDescripcion()));
        txtPais.setText(String.valueOf(objLibro.getPais().getNombre()));

        return row;
    }
}
