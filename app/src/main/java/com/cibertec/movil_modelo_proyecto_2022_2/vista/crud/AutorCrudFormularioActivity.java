package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceGrado;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCRegresar, butonregistrarcrud;


    TextView txtTituloAutor;

    String tipo;

    EditText txtNombres , txtApellidos,txtCorreo, txtFechanacimiento, txtTelefono ;

    Spinner spnCrudGrado , spnCrudPais;

    //GRADO
    ArrayAdapter<String> adaptadorGrado;
    ArrayList<String> lstNombresGrados = new ArrayList<>();

    //PAISES
    ArrayAdapter<String> adaptadorPaises;
    ArrayList<String> lstNombresPaises = new ArrayList<>();

    //SERVICES
    ServiceAutor serviceautores;

    ServiceGrado serviceGraditos;

    ServicePais servicepais;

    //OBJETO
    Autor objAutorSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_formulario);
        serviceGraditos = ConnectionRest.getConnection().create(ServiceGrado.class);
        serviceautores = ConnectionRest.getConnection().create(ServiceAutor.class);
        servicepais = ConnectionRest.getConnection().create(ServicePais.class);

        butonregistrarcrud = findViewById(R.id.btnCrudRegistrosAutor);
        txtTituloAutor = findViewById(R.id.idCruTitulosAutores);

        txtNombres = findViewById(R.id.txtCrudNombreAutor);
        txtApellidos = findViewById(R.id.txtCrudApellidosAutor);
        txtCorreo = findViewById(R.id.txtCrudCorreoAutor);
        txtFechanacimiento = findViewById(R.id.txtCrudFechaAutor);
        txtTelefono = findViewById(R.id.txtCrudTelefonoAutor);
        //SPINNERS
        spnCrudGrado = findViewById(R.id.spnCrudSpinnerGrado);
        spnCrudPais = findViewById(R.id.spnCrudSpinnPais);


      // ADAPTADORES
        adaptadorGrado = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresGrados);
        spnCrudGrado.setAdapter(adaptadorGrado);
        //ADAPTADOR
        adaptadorPaises = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPaises);
        spnCrudPais.setAdapter(adaptadorPaises);


        //PONER LAS SIGUIENTES METODOS DE ACTUALIZAR Y REGISTARR
        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargarGrado();
        cargarPaises();
        if (tipo.equals("ACTUALIZA")) {
            objAutorSeleccionado = (Autor) extras.get("var_objecto");
            txtNombres.setText(objAutorSeleccionado.getNombres());
            txtApellidos.setText(objAutorSeleccionado.getApellidos());
            txtCorreo.setText(objAutorSeleccionado.getCorreo());
            txtFechanacimiento.setText(objAutorSeleccionado.getFechaNacimiento());
            txtTelefono.setText(objAutorSeleccionado.getTelefono());
        }
        butonregistrarcrud.setText(tipo);

        txtTituloAutor.setText(titulo);
    btnCRegresar = findViewById(R.id.btnCrudRegresada);
    btnCRegresar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AutorCrudFormularioActivity.this, AutorCrudListaActivity.class);
            startActivity(intent);
        }
    });
    butonregistrarcrud.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nomb = txtNombres.getText().toString();
            String apelli = txtApellidos.getText().toString();
            String corre = txtCorreo.getText().toString();
            String fecha = txtFechanacimiento.getText().toString();
            String telef = txtTelefono.getText().toString();
            if (!nomb.matches(ValidacionUtil.TEXTO)){
                txtNombres.setError("EL NOMBRE ES DE 2 A 20 CARACTERES");
            } else if (!apelli.matches(ValidacionUtil.TEXTO)){
                txtApellidos.setError("EL CAMPO APELLIDO es de 2 a 20 caracteres");
            } else if (!corre.matches(ValidacionUtil.CORREO_GMAIL)) {
                txtCorreo.setError("EL CORREO ES DE fran@gmail.com");
            } else if (!fecha.matches(ValidacionUtil.FECHA)){
                txtFechanacimiento.setError("LA FECHA ES DE ESTE FORMATO 2004-05-05");
            } else if (!telef.matches(ValidacionUtil.CELULAR)) {
                txtTelefono.setError("EL TELEFONO ES DE 9 DIGITOS");
            } else {

                //PROGRAMANDO EN LA PARTE DE GRADO
                String textoGrado = spnCrudGrado.getSelectedItem().toString();
                String idGrado = textoGrado.split(":")[0];
                Grado objGrado = new Grado();
                objGrado.setIdGrado(Integer.parseInt(idGrado));

                //PROGRAMANDO EN LA PARTE DE PAIS
                String textoPais = spnCrudPais.getSelectedItem().toString();
                String idPaises = textoPais.split(":")[0];
                Pais objPaises = new Pais();
                objPaises.setIdPais(Integer.parseInt(idPaises));

                Autor autores = new Autor();
                autores.setNombres(nomb);
                autores.setApellidos(apelli);
                autores.setCorreo(corre);
                autores.setFechaNacimiento(fecha);
                autores.setTelefono(telef);
                autores.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                autores.setEstado(1);
                autores.setGrado(objGrado);
                autores.setPais(objPaises);

                if (tipo.equals("REGISTRA")) {
                    insertarAutores(autores);
                } else if (tipo.equals("ACTUALIZA")) {
                    Autor objAutor = (Autor) extras.get("var_objecto");
                    autores.setIdAutor(objAutor.getIdAutor());
                    actualizacionautores(autores);
                }
            }
        }
    });
    }
        public void insertarAutores (Autor objAutor){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(objAutor);
            Call<Autor> call = serviceautores.insertaAutor(objAutor);
            call.enqueue(new Callback<Autor>() {
                @Override
                public void onResponse(Call<Autor> call, Response<Autor> response) {
                 Autor salida = response.body();
                 String msg ="SE REGISTRO UN AUTOR \n";
                 msg+="ID :" + salida.getIdAutor() + "\n";
                 msg+="AUTOR : "+ salida.getNombres();
                 mensajeAlert(msg);
                }

                @Override
                public void onFailure(Call<Autor> call, Throwable t) {
                    mensajeAlert("Error al acceder al Servicio Rest1 >>> " + t.getMessage());
                }
            });

        }
        public void actualizacionautores(Autor objAutor){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(objAutor);
            Call<Autor> call = serviceautores.actualizaAutor(objAutor);
            call.enqueue(new Callback<Autor>() {
                @Override
                public void onResponse(Call<Autor> call, Response<Autor> response) {
                    Autor saliaza = response.body();
                    String msg ="Se Actualizo correctamente al Autor \n";
                    msg+="ID :"+ saliaza.getIdAutor() + "\n";
                    msg+="NOMBREDE AUTOR : "+ saliaza.getNombres() +"\n";
                    mensajeAlert(msg);
                }

                @Override
                public void onFailure(Call<Autor> call, Throwable t) {
                    mensajeAlert("Error al acceder al servicios DE AUTOR ACTUALIZAR>>" + t.getMessage());
                }
            });
        }
        public void cargarGrado(){
        Call<List<Grado>> call = serviceGraditos.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                List<Grado> lista = response.body();
                for (Grado grad : lista) {
                    lstNombresGrados.add(grad.getIdGrado() + ":" + grad.getDescripcion());
                }
                adaptadorGrado.notifyDataSetChanged();
                if (tipo.equals("ACTUALIZA")){
                    Grado objGrado = objAutorSeleccionado.getGrado();
                    String aux2 = objGrado.getIdGrado()+":"+objGrado.getDescripcion();
                    int pos = -1;
                    for (int i=0;i< lstNombresGrados.size();i++){
                        if(lstNombresGrados.get(i).equals(aux2)){
                            pos=i;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeAlert("Error al acceder al servicios Rest Grado>>>" + t.getMessage());
            }
        });
        }

        public void cargarPaises(){
        Call<List<Pais>> call = servicepais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstm = response.body();
                    for (Pais pais : lstm){
                        lstNombresPaises.add(pais.getIdPais()+":"+pais.getNombre());

                    }
                    adaptadorPaises.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Pais objPais = objAutorSeleccionado.getPais();
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos =-1;
                        for (int i=0; i<lstNombresPaises.size();i++){
                            if (lstNombresPaises.get(i).equals(aux2)){
                                pos=i;
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al servicios PAIS>>>" + t.getMessage());
            }
        });
        }
}