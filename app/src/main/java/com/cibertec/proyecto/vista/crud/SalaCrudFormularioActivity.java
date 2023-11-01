package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.entity.Sede;
import com.cibertec.proyecto.service.SedeService;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar;
    TextView  txtTitulo, txtNumero, txtPiso, txtNumAlu, txtRecursos;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnSede;
    ArrayAdapter<String> adaptadorSede;
    ArrayList<String> sedes = new ArrayList<String>();

    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidades = new ArrayList<String>();


    SedeService sedeService;
    ServiceSala salaService;
    ServiceModalidad modalidadService;

    String tipo;
    Sala objSalaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_formulario);

        btnRegresar = findViewById(R.id.btnCrudSalaRegresar);
        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtNumero = findViewById(R.id.txtCrudNumero);
        txtPiso = findViewById(R.id.txtCrudPiso);
        txtNumAlu = findViewById(R.id.txtCrudNumAlumnos);
        txtRecursos = findViewById(R.id.txtCrudRecursos);


        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudSalaEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorSede = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sedes);
        spnSede = findViewById(R.id.spnCrudSalaSede);
        spnSede.setAdapter(adaptadorSede);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidades);
        spnModalidad = findViewById(R.id.spnCrudSalaModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);

        sedeService = ConnectionRest.getConnection().create(SedeService.class);
        salaService = ConnectionRest.getConnection().create(ServiceSala.class);
        modalidadService = ConnectionRest.getConnection().create(ServiceModalidad.class);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txtTitulo.setText( txtTitulo.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")){
            objSalaSeleccionada = (Sala) extras.get("var_seleccionado");
            txtNumero.setText(objSalaSeleccionada.getNumero());
            txtPiso.setText(String.valueOf(objSalaSeleccionada.getPiso()));
            txtNumAlu.setText(String.valueOf(objSalaSeleccionada.getNumAlumnos()));
            txtRecursos.setText(objSalaSeleccionada.getRecursos());
        }

        cargaSede();
        cargaModalidad ();
        cargaEstado();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SalaCrudFormularioActivity.this,
                        SalaCrudListaActivity.class);
                  startActivity(intent);

            }
        });


    }


    public void cargaSede (){
        Call<List<Sede>> call = sedeService.listaTodos();
        call.enqueue(new Callback<List<Sede>>() {
            @Override
            public void onResponse(Call<List<Sede>> call, Response<List<Sede>> response) {
                if (response.isSuccessful()){
                    List<Sede> lstSalida = response.body();
                    sedes.clear();
                    sedes.add(" [ Seleccione Sede ] ");
                    for(Sede objSede: lstSalida){
                        sedes.add(objSede.getIdSede()  + " : " + objSede.getNombre());
                    }
                    adaptadorSede.notifyDataSetChanged();
                }
            }
           @Override
            public void onFailure(Call<List<Sede>> call, Throwable t) {}
        });
    }

    public void cargaModalidad (){
        Call<List<Modalidad>> call = modalidadService.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstSalida = response.body();
                    modalidades.clear();
                    modalidades.add(" [ Seleccione Modalidad ] ");
                    for(Modalidad objModalidad: lstSalida){
                        modalidades.add(objModalidad.getIdModalidad()  + " : " + objModalidad.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {}
        });
    }

    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objSalaSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}