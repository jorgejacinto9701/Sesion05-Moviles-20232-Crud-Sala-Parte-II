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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;

import java.util.List;

public class RevistaCrudAdapter extends ArrayAdapter<Revista> {

    private Context context;
    private List<Revista> lista;

    public RevistaCrudAdapter(@NonNull Context context, int resource, @NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_revista_crud_item, parent, false);

        TextView txtId =row.findViewById(R.id.txtCrudRevistaItemId);
        TextView txtNombre = row.findViewById(R.id.txtCrudRevistaItemNombre);
        TextView txtFrecuencia = row.findViewById(R.id.txtCrudRevistaItemFrecuencia);
        TextView txtFechaCreacion = row.findViewById(R.id.txtCrudRevistaItemFechaCreacion);
        TextView txtEstado = row.findViewById(R.id.txtCrudRevistaItemEstado);
        TextView txtModalidad = row.findViewById(R.id.txtCrudRevistaItemModalidad);
        TextView txtPais  = row.findViewById(R.id.txtCrudRevistaItemPais);

        Revista obj = lista.get(position);
        txtId.setText(String.valueOf(obj.getIdRevista()));
        txtNombre.setText(obj.getNombre());
        txtFrecuencia.setText(obj.getFrecuencia());
        txtFechaCreacion.setText(obj.getFechaCreacion());
        txtEstado.setText(String.valueOf(obj.getEstado()));
        txtModalidad.setText(obj.getModalidad().getDescripcion());
        txtPais.setText(obj.getPais().getNombre());

        return row;
    }
}
