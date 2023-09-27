 package com.cibertec.proyecto.vista.registra;


 import android.app.AlertDialog;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Spinner;
 import android.widget.Toast;

 import androidx.annotation.NonNull;

 import com.cibertec.proyecto.R;
 import com.cibertec.proyecto.entity.Pais;
 import com.cibertec.proyecto.entity.Proveedor;
 import com.cibertec.proyecto.entity.TipoProveedor;
 import com.cibertec.proyecto.service.ServicePais;
 import com.cibertec.proyecto.service.ServiceProveedor;
 import com.cibertec.proyecto.service.ServiceTipoProveedor;
 import com.cibertec.proyecto.util.ConnectionRest;
 import com.cibertec.proyecto.util.FunctionUtil;
 import com.cibertec.proyecto.util.NewAppCompatActivity;
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;

 import java.util.ArrayList;
 import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;


 public class ProveedorRegistraActivity extends NewAppCompatActivity {

    //Pais
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<>();

    //TipoProveedor
    Spinner spnTipoProveedor;
    ArrayAdapter<String> adaptadorTipoProveedor;
    ArrayList<String> tipoProveedor = new ArrayList<>();

    //Servicio
    ServiceProveedor serviceProveedor;
    ServicePais servicePais;
    ServiceTipoProveedor serviceTipoProveedor;


    //Componentes
    Button btnRegistrar;
    EditText txtRazonSoc, txtRuc, txtDireccion, txtTelefono, txtCelular, txtContacto, txtFechaReg;


     public ProveedorRegistraActivity(Proveedor objProveedor) {
     }


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_registra);

        //Conecta los servicios Rest
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);


        txtRazonSoc = findViewById(R.id.idtxtRazonSocial);
        txtRuc = findViewById(R.id.idtxtRuc);
        txtDireccion = findViewById(R.id.idtxtDireccion);
        txtTelefono = findViewById(R.id.idtxtTelefono);
        txtCelular = findViewById(R.id.idtxtCelular);
        txtContacto = findViewById(R.id.idtxtContacto);
        txtFechaReg = findViewById(R.id.idtxtFechaReg);

        adaptadorPais = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.idspnPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorTipoProveedor = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoProveedor);
        spnTipoProveedor = findViewById(R.id.idspnTipoProveedor);
        spnTipoProveedor.setAdapter(adaptadorTipoProveedor);

        cargaPais();
        cargaTipoProovedor();

         btnRegistrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String razsoc = txtRazonSoc.getText().toString();
                 String ruc = txtRuc.getText().toString();
                 String direccion = txtDireccion.getText().toString();
                 String telefono = txtTelefono.getText().toString();
                 String celular = txtCelular.getText().toString();
                 String contacto = txtContacto.getText().toString();
                 String fecha = txtFechaReg.getText().toString();
                 String idPais = spnPais.getSelectedItem().toString().split(":")[0];
                 String idTipoProveedor = spnTipoProveedor.getSelectedItem().toString().split(":")[0];

                 Pais objPais = new Pais();
                 objPais.setIdPais(Integer.parseInt(idPais));

                 TipoProveedor objTipoProveedor = new TipoProveedor();
                 objTipoProveedor.setIdTipoProveedor(Integer.parseInt(idTipoProveedor));

                 Proveedor objProveedor = new Proveedor();
                 objProveedor.setRazonsocial(razsoc);
                 objProveedor.setRuc(ruc);
                 objProveedor.setDireccion(direccion);
                 objProveedor.setTelefono(telefono);
                 objProveedor.setCelular(celular);
                 objProveedor.setContacto(contacto);
                 objProveedor.setEstado(1);
                 objProveedor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime(fecha));

                 insertaProveedor(objProveedor);
             }
         });



    }

    public void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }



    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>(){
            @Override
             public void onResponse(@NonNull Call<List<Pais>> call, @NonNull Response<List<Pais>> response){
                if (response.isSuccessful()){
                    List<Pais> lst = response.body();
                    for (Pais obj: lst){
                        paises.add(obj.getIdPais()+ ":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();;
                }else {
                    mensajeToast("Error al acceder al servicio Rest >>>");
                }
            }
            @Override
                    public void onFailure(Call<List<Pais>> call,Throwable t){
                mensajeToast("Error al acceder al servicio Rest >>>" + t.getMessage());
            }
        });
       }


    public void cargaTipoProovedor() {
        Call<List<TipoProveedor>> call = serviceTipoProveedor.listaTipoProveedor();
        call.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if (response.isSuccessful()) {
                    List<TipoProveedor> lst = response.body();
                    for (TipoProveedor obj : lst) {
                        tipoProveedor.add(obj.getIdTipoProveedor() + ":" + obj.getDescripcion());
                    }
                    adaptadorTipoProveedor.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<TipoProveedor>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

     public void insertaProveedor(Proveedor objProveedor){
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         String json = gson.toJson(objProveedor);
         mensajeAlert(json);

         Call<Proveedor> call = serviceProveedor.RegistraProveedor(objProveedor);
         call.enqueue(new Callback<Proveedor>() {
             @Override
             public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                 if (response.isSuccessful()){
                     Proveedor objsalida = response.body();
                     mensajeAlert("Registro Exitoso >>> ID >>" + objsalida.getIdProveedor()
                             + " >>> Razon Social >>> " + objsalida.getRazonsocial());
                 }else {
                     mensajeAlert(response.toString());
                 }
             }

             @Override
             public void onFailure(Call<Proveedor> call, @NonNull Throwable t) {
                 mensajeToast("Error al acceder al servicio Rest >>>" + t.getMessage());
             }
         });
     }


}