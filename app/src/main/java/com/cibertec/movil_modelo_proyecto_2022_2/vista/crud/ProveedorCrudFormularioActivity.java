package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.cibertec.movil_modelo_proyecto_2022_2.vista.registra.ProveedorRegistraActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresar, btnCrudRegistra;
    TextView txtTituloProveedor;
    EditText txtRazonSocial, txtRuc, txtDireccion, txtTelefono, txtCelular, txtContacto, txtFechaRegistro;
    Spinner spnEstado, spnPais, spnTipoProveedor;

    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> lstNombresEstado = new ArrayList<>();
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombresPais = new ArrayList<String>();
    ArrayAdapter<String> adaptadorTipoProveedor;
    ArrayList<String> lstNombresTipoProveedor = new ArrayList<String>();

    ServiceProveedor serviceProveedor;
    ServicePais servicePais;
    ServiceTipoProveedor serviceTipoProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_formulario);

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);

        btnCrudRegistra = findViewById(R.id.btnCrudRegistrar);
        txtTituloProveedor = findViewById(R.id.idCrudTituloProveedor);

        txtRazonSocial = findViewById(R.id.txtCrudRazonSocial);
        txtRuc = findViewById(R.id.txtCrudRuc);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtTelefono = findViewById(R.id.txtCrudTelefono);
        txtCelular = findViewById(R.id.txtCrudCelular);
        txtContacto = findViewById(R.id.txtCrudContacto);
        spnEstado = findViewById(R.id.spnCrudEstado);
        txtFechaRegistro = findViewById(R.id.txtCrudFechaRegistro);
        spnPais = findViewById(R.id.spnCrudPais);
        spnTipoProveedor = findViewById(R.id.spnCrudTipoProveedor);
        Locale.setDefault(new Locale("es_ES"));

        txtFechaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(
                        ProveedorCrudFormularioActivity.this,
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

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorTipoProveedor = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresTipoProveedor);
        spnTipoProveedor.setAdapter(adaptadorTipoProveedor);

        adaptadorEstado = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresEstado);
        spnEstado.setAdapter(adaptadorEstado);

        lstNombresEstado.add("1: Activo");
        lstNombresEstado.add("0: Inactivo");
        adaptadorEstado.notifyDataSetChanged();

        Bundle extras = getIntent().getExtras();
        String tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        if (tipo.equals("ACTUALIZA")) {
            Proveedor objProveedor = (Proveedor) extras.get("var_objeto");
            txtRazonSocial.setText(objProveedor.getRazonsocial());
            txtRuc.setText(objProveedor.getRuc());
            txtDireccion.setText(objProveedor.getDireccion());
            txtTelefono.setText(objProveedor.getTelefono());
            txtCelular.setText(objProveedor.getCelular());
            txtContacto.setText(objProveedor.getContacto());
            if (objProveedor.getEstado() == 0) {
                spnEstado.setSelection(1);
            } else {
                spnEstado.setSelection(0);
            }
            txtFechaRegistro.setText(objProveedor.getFechaRegistro());

            /*Pais objPais = objProveedor.getPais();
            String aux2 = objPais.getIdPais() + ":" + objPais.getNombre();
            int pos = -1;
            for (int i = 0; i < lstNombresPais.size(); i++) {
                if (lstNombresPais.get(i).equals(aux2)) {
                    pos = 1;
                    break;
                }
            }
            spnPais.setSelection(pos);

            TipoProveedor objTipoProveedor = objProveedor.getTipoProveedor();
            String aux3 = objTipoProveedor.getIdTipoProveedor() + ":" + objTipoProveedor.getDescripcion();
            int pos1 = -1;
            for (int i = 0; i < lstNombresTipoProveedor.size(); i++) {
                if (lstNombresTipoProveedor.get(i).equals(aux3)) {
                    pos1 = 1;
                    break;
                }
            }
            spnTipoProveedor.setSelection(pos1);*/
        }

        btnCrudRegistra.setText(tipo);
        txtTituloProveedor.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProveedorCrudFormularioActivity.this, ProveedorCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String razsoc = txtRazonSocial.getText().toString();
                String ruc = txtRuc.getText().toString();
                String dir = txtDireccion.getText().toString();
                String telf = txtTelefono.getText().toString();
                String cel = txtCelular.getText().toString();
                String contac = txtContacto.getText().toString();
                String est = spnEstado.getSelectedItem().toString();
                String fechreg = txtFechaRegistro.getText().toString();
                String pais = spnPais.getSelectedItem().toString();
                String tiprov = spnTipoProveedor.getSelectedItem().toString();

                if (!razsoc.matches(ValidacionUtil.TEXTO)) {
                    mensajeToast("Razon Social");
                    txtRazonSocial.setError("Este campo es obligatorio");
                } else if (!ruc.matches(ValidacionUtil.RUC)) {
                    mensajeToast("RUC");
                    txtRuc.setError("Este campo es obligatorio");
                } else if (!dir.matches(ValidacionUtil.DIRECCION)) {
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

                Pais objNewPais = new Pais();
                objNewPais.setIdPais(Integer.parseInt(pais.split(":")[0]));

                TipoProveedor objNewTipoProveedor = new TipoProveedor();
                objNewTipoProveedor.setIdTipoProveedor(Integer.parseInt(tiprov.split(":")[0]));

                Proveedor objNewProveedor = new Proveedor();
                objNewProveedor.setRazonsocial(razsoc);
                objNewProveedor.setRuc(ruc);
                objNewProveedor.setDireccion(dir);
                objNewProveedor.setTelefono(telf);
                objNewProveedor.setCelular(cel);
                objNewProveedor.setContacto(contac);
                objNewProveedor.setEstado(Integer.parseInt(est.split(":")[0]));
                objNewProveedor.setFechaRegistro(fechreg);
                objNewProveedor.setPais(objNewPais);
                objNewProveedor.setTipoProveedor(objNewTipoProveedor);

                if (tipo.equals("REGISTRA")) {
                    insertaProveedor(objNewProveedor);
                } else if (tipo.equals("ACTUALIZA")) {
                    Proveedor objAux = (Proveedor) extras.get("var_objeto");
                    objNewProveedor.setIdProveedor(objAux.getIdProveedor());
                    actualizaProveedor(objNewProveedor);
                }
                }
            }
        });
        cargaPais();
        cargaTipoProveedor();

    }

    public void insertaProveedor(Proveedor objProveedor) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objProveedor);
        // mensajeAlert(json);
        Call<Proveedor> call = serviceProveedor.insertaProveedor(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()) {
                    Proveedor objSalida = response.body();
                    String msg = "Se registró el Proveedor con éxito\n";
                    msg += "ID : " + objSalida.getIdProveedor() + "\n";
                    msg += "RAZÓN SOCIAL : " + objSalida.getRazonsocial();
                    mensajeAlert(msg);
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void actualizaProveedor(Proveedor objProveedor) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objProveedor);
        // mensajeAlert(json);
        Call<Proveedor> call = serviceProveedor.actualizaProveedor(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()) {
                    Proveedor objSalida = response.body();
                    String msg = "Se actualizó el Proveedor con exito\n";
                    msg += "ID : " + objSalida.getIdProveedor() + "\n";
                    msg += "RAZÓN SOCIAL : " + objSalida.getRazonsocial();
                    mensajeAlert(msg);
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void cargaPais() {
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> lst = response.body();
                    for (Pais obj : lst) {
                        lstNombresPais.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                } else {
                    mensajeAlert("" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaTipoProveedor() {
        Call<List<TipoProveedor>> call = serviceTipoProveedor.listaTodos();
        call.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if (response.isSuccessful()) {
                    List<TipoProveedor> lst = response.body();
                    for (TipoProveedor obj : lst) {
                        lstNombresTipoProveedor.add(obj.getIdTipoProveedor() + ":" + obj.getDescripcion());
                    }
                    adaptadorTipoProveedor.notifyDataSetChanged();
                } else {
                    mensajeAlert("" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TipoProveedor>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
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