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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
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

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresarEditorial, btnCrudRegistrarEditorial;
    TextView txtTitulo;

    EditText txtRazSocial,txtDireccion,txtRUC,txtfecCreacion;
    Spinner spnPais,spnCategoria;

    ArrayAdapter<String> adaptadorCategoria;
    ArrayAdapter<String> adaptadorPais;

    ArrayList<String> lstNombresCategoria = new ArrayList<String>();
    ArrayList<String> lstNombresPais = new ArrayList<String>();

    ServiceEditorial serviceEditorial;
    ServicePais servicePais;
    ServiceCategoria serviceCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);

        btnCrudRegistrarEditorial = findViewById(R.id.btnCrudRegistrarEditorial);
        txtTitulo = findViewById(R.id.idCrudEditorial);

        txtRazSocial = findViewById(R.id.txtCrudRazonSocial);
        txtDireccion = findViewById(R.id.txtCrudDireccionEditorial);
        txtRUC = findViewById(R.id.txtCrudRucEditorial);
        //txtfecCreacion = findViewById(R.id.txtCrudFechaCreacionEditorial);

        spnPais = findViewById(R.id.spnCrudPaisEditorial);
        spnCategoria = findViewById(R.id.spnCrudCategoriaEditorial);

        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorCategoria = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);

        Bundle extras = getIntent().getExtras();
        String tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargaCategoria();

        if (tipo.equals("Actualizar")){
            Editorial objEdit = (Editorial) extras.get("var_objeto");
            txtRazSocial.setText(objEdit.getRazonSocial());
            txtDireccion.setText(objEdit.getDireccion());
            txtRUC.setText(objEdit.getRuc());

            Categoria objCategoria = objEdit.getCategoria();
            String aux2 = objCategoria.getIdCategoria()+":"+objCategoria.getDescripcion();
            int pos = -1;
            for (int i=0; i<lstNombresCategoria.size(); i++){
                if(lstNombresCategoria.get(i).equals(aux2)){
                    pos = i;
                }
            }
            spnCategoria.setSelection(pos);


            if(objEdit.getPais().getIdPais() == 0){
                spnPais.setSelection(1);
            }else {
                spnPais.setSelection(0);
            }


        }

        btnCrudRegistrarEditorial.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresarEditorial = findViewById(R.id.btnCrudRegresarEditorial);
        btnCrudRegresarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditorialCrudFormularioActivity.this, EditorialCrudListaActivity.class);
                startActivity(intent);
            }
        });

        cargaPais();
        btnCrudRegistrarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtRazSocial.getText().toString();
                String dir = txtDireccion.getText().toString();
                String ruc = txtRUC.getText().toString();
                String pais = spnPais.getSelectedItem().toString();
                String categoria = spnCategoria.getSelectedItem().toString();

                Pais objNewPais = new Pais();
                objNewPais.setIdPais(Integer.parseInt(pais.split(":")[0]));

                Categoria objNewCategoria = new Categoria();
                objNewCategoria.setIdCategoria(Integer.parseInt(categoria.split(":")[0]));

                Editorial objNewEditorial = new Editorial();
                objNewEditorial.setRazonSocial(nom);
                objNewEditorial.setDireccion(dir);
                objNewEditorial.setRuc(ruc);
                objNewEditorial.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objNewEditorial.setPais(objNewPais);
                objNewEditorial.setCategoria(objNewCategoria);

                if(tipo.equals("Registrar")){
                    RegistrarEditorial(objNewEditorial);
                }else if(tipo.equals("Actualizar")) {
                    Editorial objAux = (Editorial) extras.get("var_objeto");
                    objNewEditorial.setIdEditorial(objAux.getIdEditorial());
                    ActualizarEditorial(objNewEditorial);
                }
            }
        });
    }

    public void RegistrarEditorial(Editorial objEditorial){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objEditorial);

        Call<Editorial> call = serviceEditorial.RegistrarEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    String msg="Se registro Editorial con exito\n";
                    msg+="Id : "+ objSalida.getIdEditorial() +"\n";
                    msg+="Razón Social : "+objSalida.getRazonSocial() +"\n";
                    msg+="Dirección : "+objSalida.getDireccion() +"\n";
                    msg+="RUC : "+objSalida.getRuc() +"\n";
                    msg+="Pais : "+objSalida.getPais().getNombre() +"\n";
                    msg+="Categoría : "+objSalida.getCategoria().getDescripcion() +"\n";
                    mensajeAlert(msg);
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error al acceder al servicio Rest " + t.getMessage());
            }
        });
    }

    public void ActualizarEditorial(Editorial objEditorial){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objEditorial);

        Call<Editorial> call = serviceEditorial.ActualizarEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    String msg="Se Actualizó Editorial con exito\n";
                    msg+="Id : "+ objSalida.getIdEditorial() +"\n";
                    msg+="Razón Social : "+objSalida.getRazonSocial() +"\n";
                    msg+="Dirección : "+objSalida.getDireccion() +"\n";
                    msg+="RUC : "+objSalida.getRuc() +"\n";
                    msg+="Pais : "+objSalida.getPais().getNombre() +"\n";
                    msg+="Categoría : "+objSalida.getCategoria().getDescripcion() +"\n";
                    mensajeAlert(msg);
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error al acceder al servicio Rest " + t.getMessage());
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
                    for(Pais obj : lst){
                        lstNombresPais.add(obj.getIdPais()+":"+obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else {
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al Acceder al Servicio Rest" + t.getMessage());
            }
        });
    }

    public void cargaCategoria(){
        Call<List<Categoria>> call = serviceCategoria.listaCategoriaDeEditorial();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> lst = response.body();
                    for(Categoria obj : lst){
                        lstNombresCategoria.add(obj.getIdCategoria()+":"+obj.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                }else {
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeAlert("Error al Acceder al Servicio Rest" + t.getMessage());
            }
        });
    }



}