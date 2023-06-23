package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;

import com.cibertec.movil_modelo_proyecto_2022_2.adapter.RevistaCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RevistaCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar, btnCrudRegistra;

    //GridView
    GridView gridCrudRevistas;
    ArrayList<Revista> data = new ArrayList<Revista>();
    RevistaCrudAdapter adaptador;

    //service
    ServiceRevista serviceRevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);

        btnCrudListar = findViewById(R.id.btnCrudListar);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistra);
        gridCrudRevistas = findViewById(R.id.gridCrudRevistas);
        adaptador = new RevistaCrudAdapter(this,R.layout.activity_revista_crud_item,data);
        gridCrudRevistas.setAdapter(adaptador);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RevistaCrudListaActivity.this,
                        RevistaCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA REVISTA");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });



        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        gridCrudRevistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Revista objRevista = data.get(position);
                Intent intent = new Intent(
                        RevistaCrudListaActivity.this,
                        RevistaCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA REVISTA");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objRevista);
                startActivity(intent);

            }
        });
        lista();
    }

    public void lista(){
        Call<List<Revista>> call = serviceRevista.listaRevista();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                List<Revista> lista =response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

            }
        });
    }

}
