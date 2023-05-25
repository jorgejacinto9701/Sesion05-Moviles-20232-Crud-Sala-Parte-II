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

public class EditorialConsultaActivity extends NewAppCompatActivity {

    //Boton
    Button btnConsultarEditorial;

    //ListView
    //ListView lstConsultaEditorial;

    //GridView
    GridView gridEditorial;
    ArrayList<Editorial> data = new ArrayList<>();
    EditorialAdapter adaptadorEditorial;

    //Servicio
    ServiceEditorial serviceEditorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_consulta);

        btnConsultarEditorial = findViewById(R.id.btnListaEditorial);

        gridEditorial = findViewById(R.id.gridEditorial);
        adaptadorEditorial = new EditorialAdapter(this, R.layout.activity_editorial_consulta_detalle, data);
        gridEditorial.setAdapter(adaptadorEditorial);

        gridEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Editorial objEditorial = data.get(position);

                Intent intent = new Intent(EditorialConsultaActivity.this, EditorialConsultaDetalleActivity.class);
                intent.putExtra("Var_Objeto", objEditorial);
                startActivity(intent);
            }
        });

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnConsultarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaEditorial();
            }
        });
    }

    public void listaEditorial(){
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
                mensajeAlert("Error al acceder al servicio Rest");
            }
        });
    }

}
