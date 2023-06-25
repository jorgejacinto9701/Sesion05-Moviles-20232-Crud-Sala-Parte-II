package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.registra.RevistaRegistraActivity;
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

public class RevistaCrudFormularioActivity extends NewAppCompatActivity {
    Button btnCrudRegresar , btnCrudRegistra;
    TextView txtTitulo;

    EditText txtNombre, txtFrecuencia , txtFechaRegistro , txtFechaCreacion;

    Spinner spnModalidad, spnPais,  spnEstado;


    ServiceModalidad serviceModalidad ;

    ServicePais servicePais ;
    ServiceRevista serviceRevista;

    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> lstNombresEstado = new ArrayList<String>();
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> lstNombresModalidad = new ArrayList<String>();

    ArrayAdapter<String> adaptadorPais;

    ArrayList<String> lstNombresPais = new ArrayList<String>();

    String  tipo;

    Revista objRevistaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_formulario);

        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnCrudRegistra = findViewById(R.id.btnCrudRegistrar);

        txtTitulo = findViewById(R.id.idCrudTituloRevista);
        txtNombre = findViewById(R.id.txtCrudNombre);
        txtFrecuencia = findViewById(R.id.txtCrudFrecuencia);
        txtFechaCreacion = findViewById(R.id.txtCrudFechaCreacion);

        Locale.setDefault(new Locale("es_ES"));
        txtFechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(
                        RevistaCrudFormularioActivity.this,
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


        spnEstado = findViewById(R.id.spnCrudRevistaEstado);
        spnModalidad = findViewById(R.id.spnCrudRevistaModalidad);
        spnPais = findViewById(R.id.spnCrudRevistaPais);

        adaptadorModalidad = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);


        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorEstado = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresEstado);
        spnEstado.setAdapter(adaptadorEstado);

        lstNombresEstado.add("1:Activo");
        lstNombresEstado.add("0:Inactivo");
        adaptadorEstado.notifyDataSetChanged();

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargaModalidad();
        cargaPais();

        if (tipo.equals("ACTUALIZA")) {
            objRevistaSeleccionado = (Revista) extras.get("var_objeto");
            txtNombre.setText(objRevistaSeleccionado.getNombre());
            txtFrecuencia.setText(objRevistaSeleccionado.getFrecuencia());
            txtFechaCreacion.setText(objRevistaSeleccionado.getFechaCreacion());
            if (objRevistaSeleccionado.getEstado() == 0) {
                spnEstado.setSelection(1);
            } else {
                spnEstado.setSelection(0);
            }
        }

        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RevistaCrudFormularioActivity.this,
                        RevistaCrudListaActivity.class);
                startActivity(intent);
            }
        });



        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNombre.getText().toString();
                String frec = txtFrecuencia.getText().toString();
                String fecCre = txtFechaCreacion.getText().toString();
                String estado = spnEstado.getSelectedItem().toString();
                String modalidad = spnModalidad.getSelectedItem().toString();
                String pais = spnPais.getSelectedItem().toString();

                if (!nom.matches(ValidacionUtil.NOMBRE)) {
                    txtNombre.setError("Nombre es de 3 a 30 caracteres");
                } else if (!frec.matches(ValidacionUtil.TEXTO)) {
                    txtFrecuencia.setError("Frecuencia es de 2 a 20 caracteres");
                } else if (!fecCre.matches(ValidacionUtil.FECHA)) {
                    txtFechaCreacion.setError("La fecha de creación es YYYY-MM-dd");
                } else {

                    Modalidad objNewModalidad = new Modalidad();
                    objNewModalidad.setIdModalidad(Integer.parseInt(modalidad.split(":")[0]));

                    Pais objNewPais = new Pais();
                    objNewPais.setIdPais(Integer.parseInt(pais.split(":")[0]));

                    Revista objNewRevista = new Revista();
                    objNewRevista.setNombre(nom);
                    objNewRevista.setFrecuencia(frec);
                    objNewRevista.setFechaCreacion(fecCre);
                    objNewRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objNewRevista.setEstado(Integer.parseInt(estado.split(":")[0]));
                    objNewRevista.setModalidad(objNewModalidad);
                    objNewRevista.setPais(objNewPais);


                    if (tipo.equals("REGISTRA")) {
                        insertaRevista(objNewRevista);
                    } else if (tipo.equals("ACTUALIZA")) {
                        Revista objAux = (Revista) extras.get("var_objeto");
                        objNewRevista.setIdRevista(objAux.getIdRevista());
                        actualizaRevista(objNewRevista);
                    }
                }
            }
        });
    }
    public void insertaRevista(Revista objRevista){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objRevista);
        // mensajeAlert(json);
        Call<Revista> call = serviceRevista.registraRevista(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if(response.isSuccessful()){
                    Revista objSalida = response.body();
                    String msg="Se registro la Revista con éxito\n";
                    msg+="CÓDIGO : "+ objSalida.getIdRevista() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombre() +"\n";
                    msg+="FRECUENCIA : "+objSalida.getFrecuencia() +"\n";
                    msg+="FECHA CREACIÓN : "+objSalida.getFechaCreacion() +"\n";
                    msg+="ESTADO : "+objSalida.getEstado();

                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }
    public void actualizaRevista(Revista objRevista){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objRevista);
        mensajeAlert(json);
        Call<Revista> call = serviceRevista.actualizaRevista(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()) {
                    Revista objSalida = response.body();
                    String msg = "Se actualizó la Revista con éxito\n";
                    msg+="CÓDIGO : " + objSalida.getIdRevista() + "\n";
                    msg+="NOMBRE : " + objSalida.getNombre() + "\n";
                    msg+="FRECUENCIA : "+objSalida.getFrecuencia() +"\n";
                    msg+="FECHA CREACIÓN : "+objSalida.getFechaCreacion() +"\n";
                    msg+="FECHA REGISTRO : "+objSalida.getFechaRegistro() +"\n";
                    msg+="ESTADO : "+objSalida.getEstado() +"\n";
                    msg+="MODALIDAD : "+objSalida.getModalidad().getDescripcion() +"\n";
                    msg+="PAÍS : "+objSalida.getPais().getNombre();
                    mensajeAlert(msg);
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaModalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if(response.isSuccessful()){
                    List<Modalidad> lst = response.body();
                    for(Modalidad obj:lst){
                        lstNombresModalidad.add(obj.getIdModalidad()+":"+obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();

                    if(tipo.equals("ACTUALIZA")){
                        Modalidad objModalidad = objRevistaSeleccionado.getModalidad();
                        String aux2 = objModalidad.getIdModalidad()+":"+objModalidad.getDescripcion();
                        int pos = -1;
                        for(int i=0; i< lstNombresModalidad.size(); i++){
                            if (lstNombresModalidad.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnModalidad.setSelection(pos);

                    }
                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());

            }

        });

    }
    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lst = response.body();
                    for(Pais obj:lst){
                        lstNombresPais.add(obj.getIdPais()+":"+obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                    if(tipo.equals("ACTUALIZA")){
                        Pais objPais = objRevistaSeleccionado.getPais();
                        String aux3 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1;
                        for(int i=0; i< lstNombresPais.size(); i++){
                            if (lstNombresPais.get(i).equals(aux3)){
                                pos = i;
                                break;
                            }
                        }
                        spnPais.setSelection(pos);

                    }
                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
}
