package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AutorAdapter;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorConsultaActivity extends NewAppCompatActivity {

    Button btnListar;
    ListView lstAutor;
    ArrayList<Autor> data = new ArrayList<>();
    AutorAdapter adaptador;

    ServiceAutor serviceAutor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_consulta);

btnListar=findViewById(R.id.btnConsulta);

lstAutor=findViewById(R.id.lstAutor);



adaptador= new AutorAdapter(this, R.layout.activity_alumno_crud_item,data);


lstAutor.setAdapter(adaptador);

serviceAutor= ConnectionRest.getConnection().create(ServiceAutor.class);



btnListar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {buscaAutor();}
});

    }

    private void buscaAutor() {

        TextInputEditText busqueda = findViewById(R.id.txtBuscar);
        String AutorInput = busqueda.getText().toString();
        Call<List<Autor>> call = serviceAutor.listaXNombre(AutorInput);

        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()){
                    List<Autor> lstAutor = response.body();

                    data.clear();
                    data.addAll(lstAutor);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {

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

}