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
import com.cibertec.proyecto.entity.Alumno;



import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_consulta_item, parent,false);

        Alumno objAlumno = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtId);
        txtID.setText("ID : " + String.valueOf(objAlumno.getIdAlumno()));

        TextView txtNombres = row.findViewById(R.id.txtNombres);
        txtNombres.setText("Nombre : " + objAlumno.getNombres());

        TextView txtApellidos = row.findViewById(R.id.txtApellidos);
        txtApellidos.setText("Apellidos : " + objAlumno.getApellidos());

        TextView txtTelefono = row.findViewById(R.id.txtTelefono);
        txtTelefono.setText("Serie : " + objAlumno.getTelefono());

        TextView txtDni = row.findViewById(R.id.txtDni);
        txtDni.setText("Dni : " + objAlumno.getDni());

        TextView txtFechaNacimiento = row.findViewById(R.id.txtFechaNacimiento);
        txtFechaNacimiento.setText("Fecha Nacimiento : " + objAlumno.getFechaNacimiento());

        TextView txtCorreo = row.findViewById(R.id.txtCorreo);
        txtCorreo.setText("Correo : " + objAlumno.getCorreo());

        TextView txtPais = row.findViewById(R.id.txtPais);
        txtPais.setText("País : " + objAlumno.getPais().getNombre());

        TextView txtModalidad = row.findViewById(R.id.txtModalidad);
        txtModalidad.setText("Modalidad : " + objAlumno.getModalidad().getDescripcion());

        return row;

    }




}