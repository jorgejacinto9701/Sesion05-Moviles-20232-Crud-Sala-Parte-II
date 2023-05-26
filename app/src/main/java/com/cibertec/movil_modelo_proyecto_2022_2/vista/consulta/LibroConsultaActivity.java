package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroConsultaActivity extends NewAppCompatActivity {

    Button btnListar;
    GridView grivLibro;
    ArrayList<Libro> data = new ArrayList<Libro>();
    LibroAdapter adapter;
    ServiceLibro serviceLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta);
        btnListar = findViewById(R.id.btnLista);
        grivLibro = findViewById(R.id.gridLibros);
        adapter= new LibroAdapter(this, R.layout.activity_item_libro_consulta, data);
        grivLibro.setAdapter(adapter);

        grivLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Libro objLibro = data.get(i);
                Intent intent = new Intent(LibroConsultaActivity.this, LibroConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO", objLibro);
                startActivity(intent);

            }
        });

        serviceLibro = new ConnectionRest().getConnection().create(ServiceLibro.class);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarLibros();
            }
        });

    }

    public void listarLibros(){
        Call<List<Libro>> call = serviceLibro.listarLibro();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if(response.isSuccessful()){
                    List<Libro> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {

            }
        });
    };


}