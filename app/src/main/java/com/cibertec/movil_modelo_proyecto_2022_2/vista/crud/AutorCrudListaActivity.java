package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AutorCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutorCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListaraut , btnCrudRegistra ;
    //GridView
    GridView gridCrudAutor;

    //service
    ServiceAutor serviceAutor;
    //ArrayList
    ArrayList<Autor> datos = new ArrayList<Autor>();
    AutorCrudAdapter adaptador;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_lista);
        //COMPONENETES
        btnCrudListaraut = findViewById(R.id.btnCrudListaAutores);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistraAutores);
        gridCrudAutor = findViewById(R.id.gridCrudAutores);
        adaptador = new AutorCrudAdapter(this,R.layout.activity_autor_crud_item,datos);
        gridCrudAutor.setAdapter(adaptador);

        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);


        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutorCrudListaActivity.this, AutorCrudFormularioActivity.class);
                startActivity(intent);
                intent.putExtra("var_titulo", "REGISTRA AUTOR");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });

        btnCrudListaraut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaAut();
            }
        });
        gridCrudAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Autor objAutor = datos.get(position);

                Intent intent = new Intent(
                        AutorCrudListaActivity.this,
                        AutorCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA AUTOR");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objecto", objAutor);
                startActivity(intent);
            }
        });

    }

    public void listaAut(){
        Call<List<Autor>> call = serviceAutor.listaAutor();
        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()){
                    List<Autor> lista =response.body();
                    datos.clear();
                    datos.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {

            }
        });
    }




}