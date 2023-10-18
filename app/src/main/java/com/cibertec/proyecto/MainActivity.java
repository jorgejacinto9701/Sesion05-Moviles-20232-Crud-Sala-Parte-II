package com.cibertec.proyecto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.adapter.RevistaAdapter;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends NewAppCompatActivity {

    //****************Para la PC2**********************

    EditText txtNombre;
    Button btnListar;

    ListView lstRevista;
    ArrayList<Revista> data = new ArrayList<>();
    RevistaAdapter adaptador;

    ServiceRevista serviceRevista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_consulta);

        txtNombre = findViewById(R.id.txtRegEdiNombre);
        btnListar = findViewById(R.id.btnLista);

        lstRevista = findViewById(R.id.lstRevista);
        adaptador = new RevistaAdapter(this, R.layout.activity_revista_crud_item, data);
        lstRevista.setAdapter(adaptador);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnListar.setOnClickListener(v -> {
            String filtro = txtNombre.getText().toString();
            consulta(filtro);
        });



    }

    public void consulta(String filtro){
        Call<List<Revista>> call = serviceRevista.listaporRevista(filtro);
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(@NonNull Call<List<Revista>> call, @NonNull Response<List<Revista>> response) {
                if (response.isSuccessful()){
                    List<Revista> lstSalida = response.body();
                    data.clear();
                    assert lstSalida != null;
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Revista>> call, @NonNull Throwable t) {
                mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });
    }



    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void mensajeToastLong(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeToastShort(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();}

}