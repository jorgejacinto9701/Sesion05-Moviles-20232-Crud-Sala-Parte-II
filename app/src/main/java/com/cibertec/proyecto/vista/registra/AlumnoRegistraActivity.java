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
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Alumno;

import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;

import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRegistraActivity extends NewAppCompatActivity {


    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidades = new ArrayList<String>();

    ServicePais servicePais;
    ServiceModalidad serviceModalidad;

    ServiceAlumno serviceAlumno;



    Button  btnidButton;

    EditText txtNombre, txtApellido, txtTelefono, txtDni, txtCorreo, txtDireccion, txtNacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_registra);


        serviceAlumno=ConnectionRest.getConnection().create(ServiceAlumno.class);
        servicePais=ConnectionRest.getConnection().create(ServicePais.class);
        serviceModalidad=ConnectionRest.getConnection().create(ServiceModalidad.class);


        txtNombre =  findViewById(R.id.txtidNombres);
        txtApellido =  findViewById(R.id.txtidApellidos);
        txtTelefono =  findViewById(R.id.txtidTelefono);
        txtDni =  findViewById(R.id.txtidDNI);
        txtCorreo =  findViewById(R.id.txtidCorreo);
        txtDireccion =  findViewById(R.id.txtidDireccion);
        txtNacimiento = findViewById(R.id.txtidNacimiento);
        btnidButton = findViewById(R.id.btnRegister);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,paises);
        spnPais = findViewById(R.id.spnidPais);
        spnPais.setAdapter((adaptadorPais));

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,modalidades);
        spnModalidad = findViewById(R.id.spnidModalidad);
        spnModalidad.setAdapter((adaptadorModalidad));

        cargaPais();
        cargarModalidad();
        btnidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String ape = txtApellido.getText().toString();
                String tel = txtTelefono.getText().toString();
                String dni = txtDni.getText().toString();
                String cor = txtCorreo.getText().toString();
                String direc = txtDireccion.getText().toString();
                String naci = txtNacimiento.getText().toString();
                String idPais = spnPais.getSelectedItem().toString().split(":")[0];
                String idModalidad = spnModalidad.getSelectedItem().toString().split(":")[0];

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                Modalidad objModalidad = new Modalidad();
                objModalidad.setIdModalidad(Integer.parseInt(idModalidad));

                Alumno objAlumno = new Alumno();
                objAlumno.setNombres(nom);
                objAlumno.setApellidos(ape);
                objAlumno.setTelefono(tel);
                objAlumno.setDni(dni);
                objAlumno.setCorreo(cor);
                objAlumno.setDireccion(direc);
                objAlumno.setFechaNacimiento(naci);
                objAlumno.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objAlumno.setEstado(1);
                objAlumno.setPais(objPais);
                objAlumno.setModalidad(objModalidad);

                insertaAlumno(objAlumno);


            }
        });

    }
    public void mensajeToast(String mensaje){
        Toast toaast1= Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toaast1.show();
    }

    public void mensajeAlert(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
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


    public void cargarModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lst =  response.body();
                    for(Modalidad obj: lst){
                        modalidades.add(obj.getIdModalidad() +":"+ obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public  void insertaAlumno(Alumno objAlumno){

        Call<Alumno> call = serviceAlumno.insertaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> >> " + objSalida.getIdAlumno()
                            + " >>> Nombre del alumno >>> " +  objSalida.getNombres());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
       }
        });
    }



}