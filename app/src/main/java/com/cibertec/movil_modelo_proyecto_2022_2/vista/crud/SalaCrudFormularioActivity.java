package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sede;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSede;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresar, btnCrudRegistra;
    TextView txtTitulo;

    EditText txtNombre, txtPiso, txtNumalu, txtRecursos;

    Spinner spnCrudSalaSede, spnCrudModalidadSede;

    ArrayAdapter<String> adaptadorSede;
    ArrayList<String> lstNombreSede = new ArrayList<String>();

    ServiceSede serviceSede;
    ServiceModalidad serviceModalidad;

    ServiceSala serviceSala;

    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> lstNombreModalidad = new ArrayList<String>();

    Sala objSalaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_formulario);

        serviceSede = ConnectionRest.getConnection().create(ServiceSede.class);
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);

        btnCrudRegistra = findViewById(R.id.btnCrudRegistrar);
        txtTitulo = findViewById(R.id.idCrudTituloSala);

        txtNombre = findViewById(R.id.txtCrudNombreDeSala);
        txtPiso = findViewById(R.id.txtCrudPisoDeSala);
        txtNumalu = findViewById(R.id.txtCrudNumeroDeAlumnosSala);
        txtRecursos = findViewById(R.id.txtCrudRecursoDeSala);
        spnCrudSalaSede = findViewById(R.id.spnCrudSalaSede);
        spnCrudModalidadSede = findViewById(R.id.spnCrudModalidadSede);

        adaptadorSede = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                lstNombreSede);
        spnCrudSalaSede.setAdapter(adaptadorSede);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                lstNombreModalidad);
        spnCrudModalidadSede.setAdapter(adaptadorModalidad);

        Bundle extras = getIntent().getExtras();
        String tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        if(tipo.equals("Actualiza")){
            objSalaSeleccionado = (Sala) extras.get("var_objeto");
            txtNombre.setText(objSalaSeleccionado.getNumero());
            txtPiso.setText(String.valueOf(objSalaSeleccionado.getPiso()));
            txtNumalu.setText(String.valueOf(objSalaSeleccionado.getNumAlumnos()));
            txtRecursos.setText(String.valueOf(objSalaSeleccionado.getRecursos()));

            if (objSalaSeleccionado.getSede().getIdSede() == 0) {
                spnCrudSalaSede.setSelection(1);
            } else {
                spnCrudSalaSede.setSelection(0);
            }

            if (objSalaSeleccionado.getModalidad().getIdModalidad() == 0) {
                spnCrudModalidadSede.setSelection(1);
            } else {
                spnCrudModalidadSede.setSelection(0);
            }
        }

        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);


        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaCrudFormularioActivity.this, SalaCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num = txtNombre.getText().toString();
                String pis = txtPiso.getText().toString();
                String numAlu = txtNumalu.getText().toString();
                String recu = txtRecursos.getText().toString();
                String sede = spnCrudSalaSede.getSelectedItem().toString();
                String moda = spnCrudModalidadSede.getSelectedItem().toString();


                Sede objNewSede = new Sede();
                objNewSede.setIdSede(Integer.parseInt(sede.split(":")[0]));

                Modalidad objNewModalidad = new Modalidad();
                objNewModalidad.setIdModalidad(Integer.parseInt(moda.split(":")[0]));

                Sala objNewSala = new Sala();
                objNewSala.setNumero(num);
                objNewSala.setPiso(Integer.parseInt(pis));
                objNewSala.setNumAlumnos(Integer.parseInt(numAlu));
                objNewSala.setRecursos(recu);
                objNewSala.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objNewSala.setEstado(1);
                objNewSala.setSede(objNewSede);
                objNewSala.setModalidad(objNewModalidad);

                if(tipo.equals("REGISTRA")){
                    insertaSala(objNewSala);
                }else if(tipo.equals("Actualiza")){
                    Sala objAux = (Sala)extras.get("var_objeto");
                    objNewSala.setIdSala(objAux.getIdSala());
                    actualizaSala(objNewSala);
                }




            }
        });

        cargaSede();
        cargaModalidad();
    }

    public void insertaSala(Sala objSala){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);

        Call<Sala> call = serviceSala.insertaSala(objSala);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if(response.isSuccessful()){
                    Sala objSalida = response.body();
                    String msg="Se registro Sala con exito\n";
                    msg+="ID : "+ objSalida.getIdSala() +"\n";
                    msg+="Numero : "+objSalida.getNumero() +"\n";
                    msg+="Piso : "+objSalida.getPiso() +"\n";
                    msg+="Alumnos : "+objSalida.getNumAlumnos() +"\n";
                    msg+="Recursos : "+objSalida.getRecursos() +"\n";
                    msg+="Sede : "+objSalida.getSede().getNombre() +"\n";
                    msg+="Modalidad : "+objSalida.getModalidad().getDescripcion() +"\n";
                    mensajeAlert(msg);
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeAlert("Error al acceder al servicio Rest >>>" + t.getMessage());
            }
        });
    }

    public void actualizaSala(Sala objSala){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);

        Call<Sala> call = serviceSala.actualizaSala(objSala);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if(response.isSuccessful()){
                    Sala objSalida = response.body();
                    String msg="Se actualizo Sala con exito\n";
                    msg+="ID : "+ objSalida.getIdSala() +"\n";
                    msg+="Numero : "+objSalida.getNumero() +"\n";
                    msg+="Piso : "+objSalida.getPiso() +"\n";
                    msg+="Alumnos : "+objSalida.getNumAlumnos() +"\n";
                    msg+="Recursos : "+objSalida.getRecursos() +"\n";
                    msg+="Sede : "+objSalida.getSede().getNombre() +"\n";
                    msg+="Modalidad : "+objSalida.getModalidad().getDescripcion() +"\n";
                    mensajeAlert(msg);
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeAlert("Error al acceder al servicio Rest >>>" + t.getMessage());
            }
        });
    }

    public void cargaSede(){
        Call<List<Sede>> call= serviceSede.listaSede();
        call.enqueue(new Callback<List<Sede>>() {
            @Override
            public void onResponse(Call<List<Sede>> call, Response<List<Sede>> response) {
                if(response.isSuccessful()){
                    List<Sede> lstSede = response.body();
                    for(Sede obj:lstSede){
                        lstNombreSede.add(obj.getIdSede()+ ":" +obj.getNombre());
                    }
                    adaptadorSede.notifyDataSetChanged();
                }else{
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Sede>> call, Throwable t) {
                mensajeAlert("Error al acceder al servicio Rest >>>" + t.getMessage());
            }
        });
    }

    public void cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaModalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if(response.isSuccessful()){
                    List<Modalidad> lstModalidad = response.body();
                    for(Modalidad obj:lstModalidad){
                        lstNombreModalidad.add(obj.getIdModalidad()+ ":" +obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeAlert("Error al acceder al servicio Rest >>>" + t.getMessage());
            }
        });
    }



}