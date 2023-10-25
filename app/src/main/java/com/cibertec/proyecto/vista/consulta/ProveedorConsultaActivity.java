package com.cibertec.proyecto.vista.consulta;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.ProveedorAdapter;
import com.cibertec.proyecto.entity.Proveedor;
import com.cibertec.proyecto.service.ServiceProveedor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorConsultaActivity extends NewAppCompatActivity {

    Button btnListar;

    ListView lstProveedor;
    ArrayList<Proveedor> data = new ArrayList<>();
    ProveedorAdapter adaptador;

    ServiceProveedor serviceProveedor;

    EditText txtfiltro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta);

        btnListar = findViewById(R.id.btnProveedorConsulta);
        txtfiltro = findViewById(R.id.txtProveedorConsulta);
        lstProveedor = findViewById(R.id.lstProveedor);
        adaptador = new ProveedorAdapter(this, R.layout.activity_proveedor_consulta_item, data);
        lstProveedor.setAdapter(adaptador);
        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);


        btnListar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                consulta();
            }
        });
    }

    void consulta(){
        String filtro=  txtfiltro.getText().toString().trim();

        Call<List<Proveedor>> call = serviceProveedor.ListaProveedor(filtro);
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                if (response.isSuccessful()){
                    List<Proveedor> lstSalida = response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {
                mensajeAlert("Mensaje => " + t.getMessage());
            }
        });
    }




    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public  void mensajeToastLong(String msg){
        Toast toast1  = Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeToastShort(String msg){
        Toast toast1 = Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public void onBackPressed() {super.onBackPressed();}
}