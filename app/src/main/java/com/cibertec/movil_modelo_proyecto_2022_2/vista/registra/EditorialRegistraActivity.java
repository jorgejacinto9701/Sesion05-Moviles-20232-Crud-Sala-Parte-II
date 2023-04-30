package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.MainActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
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


public class EditorialRegistraActivity extends NewAppCompatActivity {

    //Spinners Pais
    Spinner spnPaisEditorial;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<>();

    //Spinners Categoria
    Spinner spnCategoriaEditorial;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> catEdi = new ArrayList<>();

    //Servicios
    ServiceEditorial serviceEditorial;
    ServicePais servicePais;
    ServiceCategoria serviceCategoria;

    //Componentes de Formulario
    Button btnRegistrarEditorial;
    EditText txtRazonSocial,txtDireccionEditorial,txtRucEditorial,txtFechaCreacionEditorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_registra);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        //Para el Adaptador Pais
        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPaisEditorial = findViewById(R.id.spnPaisEditorial);
        spnPaisEditorial.setAdapter(adaptadorPais);
        CargaPais();
        //Para el Adaptador Categoria
        adaptadorCategoria = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, catEdi);
        spnCategoriaEditorial = findViewById(R.id.spnCategoriaEditorial);
        spnCategoriaEditorial.setAdapter(adaptadorCategoria);
        CargaCategoria();

        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccionEditorial = findViewById(R.id.txtDireccionEditorial);
        txtRucEditorial = findViewById(R.id.txtRucEditorial);
        txtFechaCreacionEditorial = findViewById(R.id.txtFechaCreacionEditorial);
        btnRegistrarEditorial = findViewById(R.id.btnRegistrarEditorial);
        Locale.setDefault(new Locale("es_ES"));

        txtFechaCreacionEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(
                        EditorialRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                                txtFechaCreacionEditorial.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnRegistrarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todos los Datos los Recibimos como String
                String razSoc = txtRazonSocial.getText().toString();
                String dir = txtDireccionEditorial.getText().toString();
                String ruc = txtRucEditorial.getText().toString();
                String fecCre = txtFechaCreacionEditorial.getText().toString();

                //Validaciones
                if(!razSoc.matches(ValidacionUtil.TEXTO)){
                    //mensajeToastLong("La Razón Social es de 2 a 20 Caracteres");
                    txtRazonSocial.setError("La Razón Social es de 2 a 20 Caracteres");
                } else if (!dir.matches((ValidacionUtil.DIRECCION))) {
                    txtDireccionEditorial.setError("La Dirección Social es de 3 a 30 Caracteres");
                } else if (!ruc.matches(ValidacionUtil.RUC)) {
                    txtRucEditorial.setError("El RUC es 11 Dígitos");
                } else if (!fecCre.matches(ValidacionUtil.FECHA)) {
                    txtFechaCreacionEditorial.setError("La Fecha de Creacion es YYYY-MM-dd");
                } else {
                    //Paises
                    String pais = spnPaisEditorial.getSelectedItem().toString();
                    String idPais = pais.split(" : ")[0];
                    //Categoria
                    String catEdi = spnCategoriaEditorial.getSelectedItem().toString();
                    String idCat = catEdi.split(" : ")[0];
                    //Pais
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));
                    //Categoria
                    Categoria objCategoria = new Categoria();
                    objCategoria.setIdCategoria(Integer.parseInt(idCat));

                    Editorial objEditorial = new Editorial();
                    objEditorial.setRazonSocial(razSoc);
                    objEditorial.setDireccion(dir);
                    objEditorial.setRuc(ruc);
                    objEditorial.setFechaCreacion(fecCre);
                    objEditorial.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objEditorial.setEstado(1);
                    objEditorial.setPais(objPais);
                    objEditorial.setCategoria(objCategoria);

                    RegistrarEditorial(objEditorial);
                }
            }
        });

    }

    public void RegistrarEditorial(Editorial objEditorial){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objEditorial);
        //mensajeAlert(json);

        Call<Editorial> call = serviceEditorial.RegistrarEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert(" Registro Exitoso >>> ID >> " + objSalida.getIdEditorial()
                    + " >>> Razón Social >>> " + objSalida.getRazonSocial());
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeToastLong("Error al Acceder al Servicio Rest >>>  " + t.getMessage());
            }
        });
    }

    public void CargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lstPaises = response.body();
                    for (Pais obj: lstPaises){
                        paises.add(obj.getIdPais() + " : " + obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else {
                    mensajeAlert("Error al Acceder al Servicio Rest");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {

            }
        });
    }
    public void CargaCategoria(){
        Call<List<Categoria>> call = serviceCategoria.listaCategoriaDeEditorial();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> lstCategoria = response.body();
                    for (Categoria obj: lstCategoria){
                        catEdi.add(obj.getIdCategoria() + " : " + obj.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                }else {
                    mensajeAlert("Error al Acceder al Servicio Rest");
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }

    public void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG);
        toast1.show();
    }
    public void  mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}