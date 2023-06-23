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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;

import java.util.List;

public class EditorialAdapter extends ArrayAdapter<Editorial>  {

    private Context context;
    private List<Editorial> lista;

    public EditorialAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_crud_item, parent, false);

        Editorial objEditorial = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtCrudEditorialItemId);
        txtID.setText(String.valueOf(objEditorial.getIdEditorial()));

        TextView txtRazonSocial = row.findViewById(R.id.txtCrudEditorialRazonSocial);
        txtRazonSocial.setText(objEditorial.getRazonSocial());

        TextView txtDireccion = row.findViewById(R.id.txtCrudEditorialItemDireccion);
        txtDireccion.setText(String.valueOf(objEditorial.getDireccion()));

        TextView txtRuc = row.findViewById(R.id.txtCrudEditorialItemRUC);
        txtRuc.setText(String.valueOf(objEditorial.getRuc()));

        TextView txtPais = row.findViewById(R.id.txtCrudEditorialItemPais);
        txtPais.setText(objEditorial.getPais().getNombre());

        TextView txtCategoria = row.findViewById(R.id.txtCrudEditorialItemCategoria);
        txtCategoria.setText(objEditorial.getCategoria().getDescripcion());

        return row;
    }

}
