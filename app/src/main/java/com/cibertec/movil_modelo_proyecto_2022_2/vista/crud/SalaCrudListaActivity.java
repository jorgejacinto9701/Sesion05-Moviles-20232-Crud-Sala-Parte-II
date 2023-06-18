package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.SalaCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalaCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar, btnCrudRegistra;

    GridView gridCrudSalas;

    ArrayList<Sala> data = new ArrayList<Sala>();

    SalaCrudAdapter adaptador;

    ServiceSala serviceSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_lista);

        btnCrudListar= findViewById(R.id.btnCrudListar);
        btnCrudRegistra= findViewById(R.id.btnCrudRegistra);
        gridCrudSalas = findViewById(R.id.gridCrudSalas);
        adaptador = new SalaCrudAdapter(this,R.layout.activity_sala_crud_item,data);
        gridCrudSalas.setAdapter(adaptador);

        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaCrudListaActivity.this, SalaCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA SALA");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });

        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista();
            }
        });

        gridCrudSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                Sala objSala = data.get(position);
                Intent intent = new Intent(
                        SalaCrudListaActivity.this,SalaCrudFormularioActivity.class
                );
                intent.putExtra("var_titulo", "Actualiza sala");
                intent.putExtra("var_tipo", "Actualiza");
                intent.putExtra("var_objeto", objSala);
                startActivity(intent);
            }
        });
    }

    public void lista(){
        Call<List<Sala>> call = serviceSala.listaSala();
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                List<Sala> lista = response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {

            }
        });
    }





}