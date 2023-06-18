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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;

import org.w3c.dom.Text;

import java.util.List;

public class SalaCrudAdapter extends ArrayAdapter<Sala> {

    private Context context;

    private List<Sala> lista;

    public SalaCrudAdapter(@NonNull Context context, int resource, @NonNull List<Sala> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_sala_crud_item, parent,false);

        TextView txtIdItemSala= row.findViewById(R.id.txtCrudSalaItemId);
        TextView txtNumeroItemSala = row.findViewById(R.id.txtCrudSalaItemNumero);
        TextView txtPisoItemSala = row.findViewById(R.id.txtCrudSalaItemPiso);
        TextView txtNumAluItemSala = row.findViewById(R.id.txtCrudSalaItemNumAlu);
        TextView txtRecursosSalaItem = row.findViewById(R.id.txtCrudSalaItemRecursos);
        TextView txtEstadoSalaItem = row.findViewById(R.id.txtCrudSalaItemEstado);
        TextView txtSedeSalaItem = row.findViewById(R.id.txtCrudSalaItemSede);
        TextView txtModalidadSalaItem = row.findViewById(R.id.txtCrudSalaItemModalidad);

        Sala obj = lista.get(position);
        txtIdItemSala.setText(String.valueOf(obj.getIdSala()));
        txtNumeroItemSala.setText(String.valueOf(obj.getNumero()));
        txtPisoItemSala.setText(String.valueOf(obj.getPiso()));
        txtNumAluItemSala.setText(String.valueOf(obj.getNumAlumnos()));
        txtRecursosSalaItem.setText(String.valueOf(obj.getRecursos()));
        txtEstadoSalaItem.setText(String.valueOf(obj.getEstado()));
        txtSedeSalaItem.setText(String.valueOf(obj.getSede().getNombre()));
        txtModalidadSalaItem.setText(String.valueOf(obj.getModalidad().getDescripcion()));

        return row;
    }
}
