package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroCrudListaActivity extends NewAppCompatActivity {
    Button btnCrudListaLibro , btnCrudRegistro ;
    //GridView
    GridView gridCrudLibro;


    //service
    ServiceLibro serviceLibro;
    //ArrayList

    ArrayList<Libro> datos = new ArrayList<Libro>();
    LibroCrudAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_lista);

        btnCrudListaLibro = findViewById(R.id.btnCrudListaLibro);
        btnCrudRegistro = findViewById(R.id.btnCrudRegistraLibro);
        gridCrudLibro = findViewById(R.id.gridCrud);
        adaptador = new LibroCrudAdapter(this,R.layout.activity_libro_crud_item,datos);
        gridCrudLibro.setAdapter(adaptador);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
    btnCrudRegistro.setOnClickListener((new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LibroCrudListaActivity.this, LibroCrudFormularioActivity.class);
    startActivity(intent);
    intent.putExtra("var_titulo", "REGISTRAR LIBRO");
    intent.putExtra("var_tipo", "REGISTRA");
    startActivity(intent);
        }
    }));
btnCrudListaLibro.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view) {
            lista();
        }
    });
gridCrudLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Libro objLibro = datos.get(position);
        Intent intent = new Intent(
                LibroCrudListaActivity.this,
                LibroCrudFormularioActivity.class);
        intent.putExtra("var_titulo", "ACTUALIZA LIBRO");
        intent.putExtra("var_tipo", "ACTUALIZA");
        intent.putExtra("var_objecto", objLibro);
        startActivity(intent);
    }
});

}
public void lista(){
        Call<List<Libro>> call = serviceLibro.listarLibro();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful()){
                    List<Libro> lista =response.body();
                    datos.clear();
                    datos.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {

            }
        });
    }}

