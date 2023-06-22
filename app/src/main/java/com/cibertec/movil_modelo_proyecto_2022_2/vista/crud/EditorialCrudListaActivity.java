package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListarEditorial,btnCrudRegistraEditorial;
    GridView gridCrudEditorial;
    ArrayList<Editorial> data = new ArrayList<Editorial>();
    EditorialAdapter adaptadorEditorial;

    ServiceEditorial serviceEditorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);

        btnCrudListarEditorial = findViewById(R.id.btnCrudListarEditorial);
        btnCrudRegistraEditorial = findViewById(R.id.btnCrudRegistraEditorial);

        gridCrudEditorial = findViewById(R.id.gridCrudEditorial);
        adaptadorEditorial = new EditorialAdapter(this,R.layout.activity_editorial_crud_item,data);
        gridCrudEditorial.setAdapter(adaptadorEditorial);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnCrudRegistraEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditorialCrudListaActivity.this, EditorialCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "Registrar Editorial");
                intent.putExtra("var_tipo", "Registrar");
                startActivity(intent);
            }
        });

        btnCrudListarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        gridCrudEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,EditorialCrudFormularioActivity.class
                );
                intent.putExtra("var_titulo", "Actualizar Editorial");
                intent.putExtra("var_tipo", "Actualizar");
                startActivity(intent);
            }
        });


    }
    public void lista(){
        Call<List<Editorial>> call = serviceEditorial.ListarEditorial();
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                if (response.isSuccessful()){
                    List<Editorial> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptadorEditorial.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {

            }
        });
    }

}