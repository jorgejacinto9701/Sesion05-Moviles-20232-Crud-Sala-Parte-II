package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AlumnoAdapter;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoConsultaActivity extends NewAppCompatActivity {

    Button btnListar;

    ListView lstAlumnos;
    ArrayList<Alumno> data = new ArrayList<>();
    AlumnoAdapter adaptador;

    ServiceAlumno serviceAlumno;

    EditText txtFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_consulta);

        btnListar = findViewById(R.id.idBtnLibroConsulta);
        txtFiltro = findViewById(R.id.idTxtAlumnoConsulta);

        lstAlumnos = findViewById(R.id.idLstAlumnoConsulta);
        adaptador = new AlumnoAdapter(this, R.layout.activity_alumno_consulta_item, data);
        lstAlumnos.setAdapter(adaptador);

        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulta();
            }
        });
    }

    public void consulta(){
        String filtro = txtFiltro.getText().toString().trim();

        Call<List<Alumno>> call= serviceAlumno.listaalumno(filtro);
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(response.isSuccessful()){
                    List<Alumno> lstSalida = response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                mensajeAlert("Mensaje => " + t.getMessage());
            }
        });

    }






}