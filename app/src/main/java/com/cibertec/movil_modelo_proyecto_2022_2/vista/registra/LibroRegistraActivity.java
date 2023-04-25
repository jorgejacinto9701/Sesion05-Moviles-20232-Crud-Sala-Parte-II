package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroRegistraActivity extends NewAppCompatActivity {
    EditText txtNombre;
    EditText txtAnio;
    EditText txtSerie;
    Spinner spnPais;
    Button botonRegistrar;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> arPaises = new ArrayList<>();
    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> arCategorias = new ArrayList<>();

    ServiceLibro serviceLibro;
    ServicePais servicePais;
    ServiceCategoria serviceCategoria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_registra);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arPaises);
        spnPais = findViewById(R.id.spnPaisLibro);
        spnPais.setAdapter(adaptadorPais);
        cargaPais();
        adaptadorCategoria = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arCategorias);
        spnCategoria = findViewById(R.id.spnCategoriasLibro);
        spnCategoria.setAdapter(adaptadorCategoria);
        cargaCartegoria();
        txtNombre = findViewById(R.id.txtNombreLibro);
        txtAnio = findViewById(R.id.txtAnioLibro);
        txtSerie = findViewById(R.id.txtSerieLibro);

        botonRegistrar = findViewById(R.id.btnRegistrarLibro);

        botonRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String nombre = txtNombre.getText().toString();
                String anio = txtAnio.getText().toString();
                String serie = txtSerie.getText().toString();

                if (!nombre.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("El nombre del libro debe contener de 3 a 30 caracteres");
                }else if(!anio.matches(ValidacionUtil.ANIO)){
                    mensajeAlert("Ingrese correctamente el año: 2019");
                }else if(!serie.matches(ValidacionUtil.NOMBRE)){
                    mensajeAlert("La Serie del Libro ingresada debe contener de 3 a 30 caracteres");
                }else{
                    String objPais = spnPais.getSelectedItem().toString();
                    String idPais = objPais.split(":")[0];
                    Pais mPais = new Pais();
                    mPais.setIdPais(Integer.parseInt(idPais));

                    String objCategria = spnCategoria.getSelectedItem().toString();
                    String idCategoria = objCategria.split(":")[0];
                    Categoria mCategoria = new Categoria();
                    mCategoria.setIdCategoria(Integer.parseInt(idCategoria));

                    Libro objLibro = new Libro();
                    objLibro.setTitulo(nombre);
                    objLibro.setAnio(Integer.parseInt(anio));
                    objLibro.setSerie(serie);
                    objLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objLibro.setEstado(1);
                    objLibro.setPais(mPais);
                    objLibro.setCategoria(mCategoria);
                    ingresarLibro(objLibro);
                }
            }
        });
    }

    public void ingresarLibro(Libro objLibro){

        Call<Libro> call = serviceLibro.ingresarLibro(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {

                if(response.isSuccessful()){

                    Libro salida = response.body();
                    mensajeAlert("EXITOSO - Libro Ingresado || Nombre: " + salida.getTitulo());
                }else{
                    mensajeAlert("Error del servicio || "+ response.toString());
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeToast("Error del servicio || "+ t.getMessage());
            }
        });
    };
    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> listPaises =  response.body();
                    for(Pais obj: listPaises){
                        arPaises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error del servicio");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error del servicio " + t.getMessage());
            }
        });
    };

    public void cargaCartegoria(){
        Call<List<Categoria>> call = serviceCategoria.listaCategoriaDeLibro();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> listCatergoria = response.body();
                    for(Categoria obj: listCatergoria){
                        arCategorias.add(obj.getIdCategoria() + ":" + obj.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                }else{
                    mensajeToast("Error del servicio");
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast("Error del servicio || "+ t.getMessage());
            }
        });
    };
    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }
}