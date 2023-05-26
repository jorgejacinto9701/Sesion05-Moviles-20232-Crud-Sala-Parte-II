package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;//
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;//
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceGrado;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorRegistraActivity extends NewAppCompatActivity {
    Spinner spnPais;
    Spinner spnGrado;

    //adaptadores
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();
    //
    ArrayAdapter<String>adaptadorGrado;
    ArrayList<String> grados = new ArrayList<String>();

    //service

    ServiceAutor serviceAutor;
    ServiceGrado serviceGrado;
    ServicePais servicePais;



//

    Button btnRegistrar;
    EditText txtNombres , txtApellidos, txtCorreo ,txtFechaN ,txtNumero;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_registra);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceGrado= ConnectionRest.getConnection().create(ServiceGrado.class);
        serviceAutor=ConnectionRest.getConnection().create(ServiceAutor.class);



        ///pais

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,paises);
        spnPais =findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptadorPais);

        cargaPais();

        //grado
        adaptadorGrado= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,grados);
        spnGrado =findViewById(R.id.spnGrado);
        spnGrado.setAdapter(adaptadorGrado);

        cargaGrado();

        txtNombres =findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        txtFechaN =findViewById(R.id.txtFechaNacimiento);
        txtNumero =findViewById(R.id.txtTelefono);
        btnRegistrar =findViewById(R.id.btnRegistrar);
        //

        txtFechaN.setOnClickListener(new View.OnClickListener(){

            @Override
            public void  onClick(View view){
                Calendar myCalendar=Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd",new Locale("es"));


                new DatePickerDialog(
                        AutorRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                                txtFechaN.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String nomb = txtNombres.getText().toString();
                String appe = txtApellidos.getText().toString();
                String corr = txtCorreo.getText().toString();
                String fech = txtFechaN.getText().toString();
                String num = txtNumero.getText().toString();
                String tGrado = spnGrado.getSelectedItem().toString();
                String idGrado = tGrado.split(":")[0];
                String tpais = spnPais.getSelectedItem().toString();
                String idPais = tpais.split(":")[0];






                if (!nomb.matches(ValidacionUtil.NOMBRE)) {

                    txtNombres.setError("Ingrese Nombres   de 1 hasta 30 caracteres");
                } else if (!appe.matches(ValidacionUtil.APELLIDOS)) {

                    txtApellidos.setError("Ingrese apellidos   de 1 hasta 30 caracteres");

                } else if (!corr.matches(ValidacionUtil.CORREO)) {

                    txtCorreo.setError("Ingrese correo electrónico");

                } else if (!fech.matches(ValidacionUtil.FECHA)) {

                    txtFechaN.setError("Formato de fecha es YYYY-MM-dd");

                } else if (!num.matches(ValidacionUtil.TELEFONO)) {

                    txtNumero.setError("número es de 9 digitos");

                } else if (spnGrado.getSelectedItem().toString().equals("Seleccione un grado")) {
                    mensajeAlert("seleccione un grado");

                } else if (spnPais.getSelectedItem().toString().equals("Seleccione país")) {
                    mensajeAlert("Introduzca un país");

                } else {



                    Grado objGrado = new Grado();
                    objGrado.setIdGrado(Integer.parseInt(idGrado));

                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    Autor objAutor = new Autor();
                    objAutor.setNombres(nomb);
                    objAutor.setApellidos(appe);
                    objAutor.setCorreo(corr);
                    objAutor.setFechaNacimiento(fech);
                    objAutor.setTelefono(num);
                    objAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objAutor.setEstado(1);
                    objAutor.setGrado(objGrado);
                    objAutor.setPais(objPais);


                    insertaAutor(objAutor);
                    limpiar();

                }

            }

        });

    }

    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstpaises = response.body();

                    paises.add("Seleccione país");
                    for (Pais p: lstpaises){
                        paises.add(p.getIdPais() +":"+ p.getNombre());

                    }
                    adaptadorPais.notifyDataSetChanged();

                }else{
                    mensajeToast("Error  al servicio rest >>>>");


                }

            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error  al servicio rest >>>>" );

            }
        });

    }

    public  void  cargaGrado(){
        Call<List<Grado>> call = serviceGrado.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if(response.isSuccessful()) {
                    List<Grado> lstGrado = response.body();
                    grados.add("Seleccione un grado");
                    for (Grado g : lstGrado) {
                        grados.add(g.getIdGrado() + ":" + g.getDescripcion());

                    }
                    adaptadorGrado.notifyDataSetChanged();
                }else{
                    mensajeToast("Error  al servicio rest >>>>");


                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error  al servicio rest >>>>" );


            }
        });



    }

    public  void insertaAutor (Autor oaut) {

        Call<Autor> call = serviceAutor.insertaAutor(oaut);

        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()) {
                    Autor objAutor = response.body();
                    String msg = " Se registro un autor " + "\n ";
                    msg+= "id registrado : " + objAutor.getIdAutor() + "\n";
                    msg+= "Nombres : " + objAutor.getNombres() + "\n";
                    msg+= "Apellidos: " + objAutor.getApellidos() + "\n";
                    msg+= "Correo : " + objAutor.getCorreo() + "\n";
                    mensajeAlert(msg);


                } else {

                    mensajeToast("no se accedio al rest >>>" );

                }

            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToast("No registro" );


            }
        });
    }
    public void mensajeToast(String mensaje){

        Toast toast1 =Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG);
        toast1.show();

    }



    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void limpiar(){
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtFechaN.setText("");
        txtNumero.setText("");
        spnGrado.setSelection(0);
        spnPais.setSelection(0);


    }


}

