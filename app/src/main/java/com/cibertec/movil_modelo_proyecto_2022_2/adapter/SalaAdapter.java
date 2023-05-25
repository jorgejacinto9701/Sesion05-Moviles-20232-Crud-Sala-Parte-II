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


import java.util.List;

public class SalaAdapter extends ArrayAdapter<Sala>  {

    private Context context;
    private List<Sala> lista;

    public SalaAdapter(@NonNull Context context, int resource, @NonNull List<Sala> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_item_sala, parent, false);

        TextView txtId = row.findViewById(R.id.txtIdSala);
        TextView txtNumero = row.findViewById(R.id.txtNumeroSala);
        TextView txtPiso = row.findViewById(R.id.txtPisoSala);
        TextView txtNumAlu = row.findViewById(R.id.txtNumAlumnos);
        TextView txtRecu = row.findViewById(R.id.txtRecurso);
        TextView txtEsta = row.findViewById(R.id.txtEstado);

        Sala objSala = lista.get(position);
        txtId.setText(String.valueOf(objSala.getIdSala()));
        txtNumero.setText(objSala.getNumero());
        txtPiso.setText(String.valueOf(objSala.getPiso()));
        txtNumAlu.setText(String.valueOf(objSala.getNumAlumnos()));
        txtRecu.setText(String.valueOf(objSala.getRecursos()));
        txtEsta.setText(String.valueOf(objSala.getEstado()));



        return row;
    }
}
