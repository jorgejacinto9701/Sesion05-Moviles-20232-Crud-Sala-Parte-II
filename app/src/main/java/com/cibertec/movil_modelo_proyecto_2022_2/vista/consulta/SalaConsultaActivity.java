package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.MainActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.SalaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaConsultaActivity extends NewAppCompatActivity {

    //Boton
    Button btnListar;

    //ListView
    GridView gridSala;
    ArrayList<Sala> data = new ArrayList<Sala>();
    SalaAdapter adaptador;

    //Servicio
    ServiceSala serviceSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta);

        btnListar = findViewById(R.id.btnLista);
        gridSala= findViewById(R.id.gridSala);
        adaptador = new SalaAdapter(this, R.layout.activity_item_sala, data);
        gridSala.setAdapter(adaptador);

        gridSala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Sala objSala = data.get(position);

               Intent intent = new Intent(SalaConsultaActivity.this, SalaConsultaDetalleActivity.class);
               intent.putExtra("VAR_OBJETO", objSala);
               startActivity(intent);

            }
        });

        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {listarSala();}
        });
    }

    public void listarSala(){
        Call<List<Sala>> call = serviceSala.listaSala();
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if(response.isSuccessful()){
                    List<Sala> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {

            }
        });
    }




}