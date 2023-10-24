package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.proyecto.R;
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
    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_consulta_item, parent, false);

        Editorial objEditorial= lista.get(position);

        TextView txtID = row.findViewById(R.id.txtID);
        txtID.setText("ID: " + String.valueOf(objEditorial.getIdEditorial()));

        TextView txtRazonSocial = row.findViewById(R.id.txtRazonSocial);
        txtRazonSocial.setText("Razon Social :"+objEditorial.getRazonSocial());

        TextView txtDireccion = row.findViewById(R.id.txtiDirecciontxtiDireccion);
        txtDireccion.setText("Direccion :"+objEditorial.getDireccion());

        TextView txtRUC = row.findViewById(R.id.txtRUC);
        txtRUC.setText("RUC :"+objEditorial.getRuc());

        TextView txtFechaCreacion = row.findViewById(R.id.txtFechaCreacion);
        txtFechaCreacion.setText("Fecha Creacion  :"+objEditorial.getFechaCreacion());

        TextView txtFechaRegistro = row.findViewById(R.id.txtFechaRegistro);
        txtFechaRegistro.setText("Fecha de Registro  :"+objEditorial.getFechaRegistro());

        return row;
    }

}
