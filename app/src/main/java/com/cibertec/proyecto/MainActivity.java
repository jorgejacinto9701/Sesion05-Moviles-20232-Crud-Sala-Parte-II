package com.cibertec.proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    Button btnListar;

    ListView lstRevista;
    ArrayList<Revista> data = new ArrayList<>();
    RevistaAdapter adaptador;

    ServiceRevista serviceRevista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_consulta);


        btnListar = findViewById(R.id.btnLista);

        lstRevista = findViewById(R.id.lstRevista);
        adaptador = new RevistaAdapter(this, R.layout.activity_revista_crud_item, data);
        lstRevista.setAdapter(adaptador);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaRevista();
            }
        });



    }

    public void listaRevista(){
        Call<List<Revista>> call = serviceRevista.listaRevista();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if (response.isSuccessful()){
                    List<Revista> lstSalida = response.body();
                    mensajeAlert(""+lstSalida.size());
                    data.clear();;
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

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