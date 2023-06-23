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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroCrudFormularioActivity extends NewAppCompatActivity {
    Button btnCrudrregresar, btnCrudregistra;


    TextView txtTitulo;

    String tipo;
    EditText txttitulazo, txtAnio, txtSerie;
    Spinner spnCrudPais, spnCrudCategoria;
    //Pais
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNomPais = new ArrayList<>();

    //Categoria

    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> lstNombresCategoria = new ArrayList<>();

    //Service
    ServiceCategoria serviceCategoria;
    ServicePais servicePais;
    ServiceLibro serviceLibro;

    //Libro
    Libro objLibroSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_formulario);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);

        btnCrudregistra = findViewById(R.id.btnCrudRegistrarLibro);
        txtTitulo = findViewById(R.id.idCrudTituloLibro);

        txttitulazo = findViewById(R.id.txtCrudTitulo);
        txtAnio = findViewById(R.id.txtCrudAnio);
        txtSerie = findViewById(R.id.txtSerieCrudLibro);


        spnCrudCategoria = findViewById(R.id.spnCrudSpinnerCategoria);
        spnCrudPais = findViewById(R.id.spnCrudSpinnerPais);

        //adaptador Categoria
        adaptadorCategoria = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresCategoria);
        spnCrudCategoria.setAdapter(adaptadorCategoria);
        //adaptador pais
        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNomPais);
        spnCrudPais.setAdapter(adaptadorPais);
        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");


        cargaPais();
        cargaCategoria();

        if (tipo.equals("ACTUALIZA")) {
            objLibroSeleccionado = (Libro) extras.get("var_objecto");
            txttitulazo.setText(objLibroSeleccionado.getTitulo());
            txtAnio.setText(String.valueOf(objLibroSeleccionado.getAnio()));
            txtSerie.setText(objLibroSeleccionado.getSerie());
        }
        btnCrudregistra.setText(tipo);

        txtTitulo.setText(titulo);

        btnCrudrregresar = findViewById(R.id.btnCrudRegresarLista);
        btnCrudrregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibroCrudFormularioActivity.this, LibroCrudListaActivity.class);
                startActivity(intent);
            }

        });
        btnCrudregistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titu = txttitulazo.getText().toString();
                String anio = txtAnio.getText().toString();
                String serie = txtSerie.getText().toString();
                if (!titu.matches(ValidacionUtil.TEXTO)){
                    txttitulazo.setError("EL TITULO ES DE 2 A 20 CARACTERES");
                }   else if (!anio.matches(ValidacionUtil.ANIO)){
                    txtAnio.setError("EL AÑO ES DE 2004");
                } else if (!serie.matches(ValidacionUtil.TEXTO)){
                    txtSerie.setError("LA SERIE ES DE 2 A 20 CARACTERES");
                }





                String textoPais = spnCrudPais.getSelectedItem().toString();
                String idPais = textoPais.split(":")[0];
                Pais objPaises = new Pais();
                objPaises.setIdPais(Integer.parseInt(idPais));


                String textoCate = spnCrudCategoria.getSelectedItem().toString();
                String idCat = textoCate.split(":")[0];
                Categoria objCategoria = new Categoria();
                objCategoria.setIdCategoria(Integer.parseInt(idCat));

                Libro objnewLibro = new Libro();
                objnewLibro.setTitulo(titu);
                objnewLibro.setAnio(Integer.parseInt(anio));
                objnewLibro.setSerie(serie);
                objnewLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objnewLibro.setEstado(1);
                objnewLibro.setPais(objPaises);
                objnewLibro.setCategoria(objCategoria);

                if(tipo.equals("REGISTRA")){
                    insertarLibro(objnewLibro);
                }else if (tipo.equals("ACTUALIZA")){
                    Libro objAux = (Libro) extras.get("var_objecto");
                    objnewLibro.setIdLibro(objAux.getIdLibro());
                    actualizarLibro(objnewLibro);



                    //

                }

            }
        });
    }
    public void insertarLibro(Libro objLibro){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objLibro);
        Call<Libro> call = serviceLibro.ingresarLibro(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objSalida = response.body();
                    String msg ="Se registro su LIBRO con éxito :)\n";
                    msg+="ID :"+ objSalida.getIdLibro() + "\n";
                    msg+="LIBRO NOMBRE :"+objSalida.getTitulo();
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }


            @Override
            public void onFailure(Call<Libro> call, Throwable t) {

            }
        });

    }
    public void actualizarLibro(Libro objLibro){
        Gson gson =new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objLibro);
        Call<Libro> call = serviceLibro.actualizaLibros(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objSalidita = response.body();
                    String msg ="Se Actualizo correctamente el libro :)\n";
                    msg+="cod :"+ objSalidita.getIdLibro() + "\n";
                    msg+="TITULO"+ objSalidita.getTitulo() +"\n";
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeAlert("Error al acceder al servicios ACTUALIZAR>>" + t.getMessage());
            }
        });
    }
    public void cargaPais() {
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> lista = response.body();
                    for (Pais alm : lista) {
                        lstNomPais.add(alm.getIdPais() + ":" + alm.getNombre());

                    }
                    adaptadorPais.notifyDataSetChanged();
                    if(tipo.equals("ACTUALIZA")){
                        Pais objPais = objLibroSeleccionado.getPais();
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1;
                        for(int i=0; i< lstNomPais.size(); i++){
                            if(lstNomPais.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnCrudPais.setSelection(pos);
                    }
                }else{
                    mensajeAlert(""+response.message());
                }

            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al servicios Rest 1>>>" + t.getMessage());
            }
        });
    }
    public void cargaCategoria() {
        Call<List<Categoria>> call = serviceCategoria.listaCategoriaDeLibro();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    List<Categoria> lstm = response.body();
                    for (Categoria modl : lstm) {
                        lstNombresCategoria.add(modl.getIdCategoria() + ":" + modl.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")) {
                        Categoria objCat = objLibroSeleccionado.getCategoria();
                        String aux2 = objCat.getIdCategoria() + ":" + objCat.getDescripcion();
                        int pos = -1;
                        for (int i = 0; i < lstNombresCategoria.size(); i++) {
                            if (lstNombresCategoria.get(i).equals(aux2)) {
                                pos = i;
                                break;
                            }
                        }
                        spnCrudCategoria.setSelection(pos);
                    }
                } else {
                    mensajeAlert("" + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeAlert("Error al acceder al servicios Rest 2>>>" + t.getMessage());
            }
        });
    }
    }




