package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Grado;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.service.ServiceGrado;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorRegistraActivity extends NewAppCompatActivity {

    //Pais
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new  ArrayList<String>();

    //Grado
    Spinner spnGrado;
    ArrayAdapter<String> adaptadorGrado;
    ArrayList<String> grados = new  ArrayList<String>();

//Servicios

    ServicePais servicioPais;
    ServiceGrado servicioGrado;
    ServiceAutor servicioAutor;

//Componentes Formulario Registro Autor

    EditText txtNombre, txtApellido, txtCorreo, txtFechaNac, txtTelefono;
    Button btnRegistrar;

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void cargaPais(){
        Call<List<Pais>> call = servicioPais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lst =  response.body();
                    for(Pais obj: lst){
                        paises.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaGrado(){
        Call<List<Grado>> call=servicioGrado.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()){
                    List<Grado> lista=response.body();
                    for(Grado obj:lista){
                        grados.add(obj.getIdGrado()+":"+obj.getDescripcion() );
                    }
                    adaptadorGrado.notifyDataSetChanged();
                }else {
                    mensajeToast("Error al acceder al servicio Rest");
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error al acceder al servicio rest "+ t.getMessage());
            }
        });

    }

    public void insertarAutor(Autor objAutor) {
        Call<Autor> call=servicioAutor.insertaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida=response.body();
                    mensajeAlert("Registro Exitoso >>>ID>>> "+ objSalida.getIdAutor()+">>>Autor: >>>"+
                            objSalida.getNombres());
                }else {
                    mensajeToast(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest "+t.getMessage());
            }
        });


    }



    //--------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_registra);

//Conexion de servicios REST(Autor)
        servicioAutor= ConnectionRest.getConnection().create(ServiceAutor.class);
        servicioGrado= ConnectionRest.getConnection().create(ServiceGrado.class);
        servicioPais=ConnectionRest.getConnection().create(ServicePais.class);
        //Relacionado de variables con variables en GUI
        txtNombre= findViewById(R.id.txtNombres);
        txtApellido= findViewById(R.id.txtApellidos);
        txtCorreo= findViewById(R.id.txtCorreo);
        txtTelefono= findViewById(R.id.txtTelefono);
        txtFechaNac= findViewById(R.id.txtFechaNacimiento);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spinnerPais);
        spnPais.setAdapter(adaptadorPais);


        adaptadorGrado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grados);
        spnGrado = findViewById(R.id.spinnerGrado);
        spnGrado.setAdapter(adaptadorGrado);

        cargaGrado();
        cargaPais();




    }



}