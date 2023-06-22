package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaRegistraActivity extends NewAppCompatActivity {


    Spinner spnModalidad, spnPaises;
    ArrayAdapter<String> adaptadorPais;

    ArrayAdapter<String> adaptadorModalidad;

    ArrayList<String> paises = new ArrayList<String>();

    ArrayList<String> modalidad = new ArrayList<String>();

   //Servicio
    ServiceRevista serviceRevista;

    ServicePais  servicePais;

    ServiceModalidad serviceModalidad;

    //Componentes del formulario

    EditText txtNombre, txtFrecuencia, txtFechaCreacion;

    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_registra);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
       //Adaptador paises
        adaptadorPais = new ArrayAdapter<String>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,paises);
        spnPaises = findViewById(R.id.spnPaises);
        spnPaises.setAdapter(adaptadorPais);

        cargaPais();

        //Adaptador modalidad
        adaptadorModalidad = new ArrayAdapter<String>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,modalidad);
        spnModalidad = findViewById(R.id.spnModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);
        cargaModalidad();


        txtNombre = findViewById(R.id.txtNombre);
        txtFrecuencia = findViewById(R.id.txtFrecuencia);
        txtFechaCreacion = findViewById(R.id.txtFechaCreacion);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        Locale.setDefault(new Locale("es_ES"));
        txtFechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(
                        RevistaRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                                txtFechaCreacion.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Datos recibidos como String
                String nom = txtNombre.getText().toString();
                String fre = txtFrecuencia.getText().toString();
                String fecCre = txtFechaCreacion.getText().toString();



                if (!nom.matches(ValidacionUtil.NOMBRE)) {
                    txtNombre.setError("Nombre es de 3 a 30 caracteres");
                } else if (!fre.matches(ValidacionUtil.TEXTO)) {
                    txtFrecuencia.setError("Frecuencia es de 2 a 20 caracteres");
                } else if (!fecCre.matches(ValidacionUtil.FECHA)) {
                    txtFechaCreacion.setError("La fecha de creación es YYYY-MM-dd");
                }else{
                    String paises = spnPaises.getSelectedItem().toString();
                    String idPais = paises.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    String Modalidad = spnModalidad.getSelectedItem().toString();
                    String idModalidad = Modalidad.split(":")[0];
                    Modalidad objModalidad = new Modalidad();
                    objModalidad.setIdModalidad(Integer.parseInt(idModalidad));


                    Revista objRevista = new Revista();

                    objRevista.setNombre(nom);
                    objRevista.setFrecuencia(fre);
                    objRevista.setFechaCreacion(fecCre);
                    objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objRevista.setEstado(1);
                    objRevista.setPais(objPais);
                    objRevista.setModalidad(objModalidad);


                    registraRevista(objRevista);

                }



            }
        });


    }
    public void registraRevista(Revista objRevista){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objRevista);
        mensajeAlert(json);


        Call<Revista> call = serviceRevista.registraRevista(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdRevista()
                            + " >>> Nombre >>> " +  objSalida.getNombre());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }

        });

    }
    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lstPaises = response.body();
                    for(Pais obj: lstPaises){
                        paises.add(obj.getIdPais() +":"+obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>>");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>>" + t.getMessage());

            }
        });
    }
    public void cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaModalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if(response.isSuccessful()){
                    List<Modalidad> lstModalidad= response.body();
                    for(Modalidad obj: lstModalidad){
                        modalidad.add(obj.getIdModalidad() +":"+obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>>");
                }
            }

            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>>" + t.getMessage());

            }
        });

    }

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}

