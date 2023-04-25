package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.TipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceTipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorRegistraActivity extends NewAppCompatActivity {

    Spinner spnPais;
    Spinner spnTipoProveedor;
    ArrayAdapter<String> adapterUno;
    ArrayAdapter<String> adapterDos;

    ArrayList<String> paises = new ArrayList<String>();

    ArrayList<String> tipoProveedores = new ArrayList<String>();

    ServiceProveedor serviceProveedor;
    ServicePais servicePais;
    ServiceTipoProveedor serviceTipoProveedor;

    Button btnRegistrar;

    EditText txtRazonSocial, txtRuc, txtDireccion, txtTelefono, txtCelular, txtContacto, txtFechaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_registra);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);
        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);

        adapterUno = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adapterUno);

        adapterDos = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoProveedores);
        spnTipoProveedor = findViewById(R.id.spnProveedor);
        spnTipoProveedor.setAdapter(adapterDos);

        cargaPais();

        cargaTipoProveedor();

        txtRazonSocial = findViewById(R.id.txtRegRazonSocial);
        txtRuc = findViewById(R.id.txtRegRuc);
        txtDireccion = findViewById(R.id.txtRegDireccion);
        txtTelefono = findViewById(R.id.txtRegTelefono);
        txtCelular = findViewById(R.id.txtRegCelular);
        txtContacto = findViewById(R.id.txtRegContacto);
        txtFechaRegistro = findViewById(R.id.txtRegFechRegistro);
        Locale.setDefault(new Locale("es_ES"));

        txtFechaRegistro.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(
                        ProveedorRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", new Locale("es"));
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                                txtFechaRegistro.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String razSoc = txtRazonSocial.getText().toString();
                String ruc = txtRuc.getText().toString();
                String direc = txtDireccion.getText().toString();
                String telf = txtTelefono.getText().toString();
                String cel = txtCelular.getText().toString();
                String contac = txtContacto.getText().toString();
                String fechReg = txtFechaRegistro.getText().toString();

                if (!razSoc.matches(ValidacionUtil.TEXTO)) {
                    mensajeToast("Razon Social");
                    txtRazonSocial.setError("Este campo es obligatorio");
                } else if (!ruc.matches(ValidacionUtil.RUC)) {
                    mensajeToast("RUC");
                    txtRuc.setError("Este campo es obligatorio");
                } else if (!direc.matches(ValidacionUtil.DIRECCION)) {
                    mensajeToast("Dirección");
                    txtDireccion.setError("Este campo es obligatorio");
                } else if (!telf.matches(ValidacionUtil.TELEFONO)) {
                    mensajeToast("Teléfono");
                    txtTelefono.setError("Este campo es obligatorio");
                } else if (!cel.matches(ValidacionUtil.CELULAR)) {
                    mensajeToast("Célular");
                    txtCelular.setError("Este campo es obligatorio");
                } else if (!contac.matches(ValidacionUtil.NOMBRE)) {
                    mensajeToast("Contacto");
                    txtContacto.setError("Este campo es obligatorio");
                } else  {

                    String pais = spnPais.getSelectedItem().toString();
                    String idPais = pais.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    String tipProv = spnTipoProveedor.getSelectedItem().toString();
                    String idTipProv = tipProv.split(":")[0];
                    TipoProveedor objTipProv = new TipoProveedor();
                    objTipProv.setIdTipoProveedor(Integer.parseInt(idTipProv));

                    Proveedor objProv = new Proveedor();
                    objProv.setRazonsocial(razSoc);
                    objProv.setRuc(ruc);
                    objProv.setDireccion(direc);
                    objProv.setTelefono(telf);
                    objProv.setCelular(cel);
                    objProv.setContacto(contac);
                    objProv.setEstado(1);
                    objProv.setFechaRegistro(fechReg);
                    objProv.setPais(objPais);
                    objProv.setTipoProveedor(objTipProv);

                    insertaProveedor(objProv);

                }
            }
        });
    }

    public  void insertaProveedor(Proveedor objProveedor){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objProveedor);
        mensajeAlert(json);

        Call<Proveedor> call = serviceProveedor.insertaProveedor(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdProveedor()
                            + " >>> Razón Social >>> " +  objSalida.getRazonsocial());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaPais() {
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> lstPaises = response.body();
                    for (Pais obj : lstPaises) {
                        paises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adapterUno.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaTipoProveedor() {
        Call<List<TipoProveedor>> call = serviceTipoProveedor.listaTodos();
        call.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if (response.isSuccessful()) {
                    List<TipoProveedor> lstTipoProveedor = response.body();
                    for (TipoProveedor obj : lstTipoProveedor) {
                        tipoProveedores.add(obj.getIdTipoProveedor() + ":" + obj.getDescripcion());
                    }
                    adapterDos.notifyDataSetChanged();
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

    public void mensajeToast(String mensaje) {
        Toast toast1 = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


}