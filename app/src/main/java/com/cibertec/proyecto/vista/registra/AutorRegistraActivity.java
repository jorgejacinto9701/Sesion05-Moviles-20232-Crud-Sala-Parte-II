package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.AttachedSurfaceControl;
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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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


        btnRegistrar=findViewById(R.id.btnRegistrarAutor);


        cargaGrado();
        cargaPais();


        //TimeZone timeZone = TimeZone.getTimeZone("America/Lima");
        //TimeZone timeZone = TimeZone.getTimeZone("GMT-05");
        //SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // formatoFecha.setTimeZone(timeZone);
        //String fechaActual = formatoFecha.format(new Date());

        String fechaActual = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //crear variables y obtener datos de las cajas y spinners
        String nom = txtNombre.getText().toString();
        String ape = txtApellido.getText().toString();
        String correo = txtCorreo.getText().toString();
        String fnac= txtFechaNac.getText().toString();
        String fono = txtTelefono.getText().toString();
        //obtener el id de los spinners separando lo obtenido por ":" y obteniendo el primer parametro
        //valor obtenido en String
        String idPais = spnPais.getSelectedItem().toString().split(":")[0];
        String idGrado = spnGrado.getSelectedItem().toString().split(":")[0];

        // se genera un objeto de la clase correspondiente y
        // convierten el valor obtenido a INT
        Pais objPais = new Pais();
        objPais.setIdPais(Integer.parseInt(idPais));

        Grado objGrado = new Grado();
        objGrado.setIdGrado(Integer.parseInt(idGrado));

        //se procede a setear los datos
        Autor objAutor = new Autor();
        objAutor.setNombres(nom);
        objAutor.setApellidos(ape);
        objAutor.setCorreo(correo);
        objAutor.setFechaNacimiento(fnac);
        objAutor.setTelefono(fono);
        //se retorna los valores int de los spinners
        objAutor.setPais(objPais);
        objAutor.setGrado(objGrado);
        //se asigna estado 1
        objAutor.setEstado(1);

        //para fecha de registro se llama a la funcion desde la carpeta util
        //objAutor.setFechaRegistro(FunctionUtil.getFechaActualString()); (fallas)
        objAutor.setFechaRegistro(fechaActual);

        //invoca al metodo insertarAutor y se envia datos del objeto Autor
        insertarAutor(objAutor);






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
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest "+t.getMessage());
            }
        });


    }


}