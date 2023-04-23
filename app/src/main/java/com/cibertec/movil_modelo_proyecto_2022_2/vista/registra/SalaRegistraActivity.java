package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sede;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSede;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaRegistraActivity extends NewAppCompatActivity {

    Spinner spnSede;
    ArrayAdapter<String> SalaAdapter;
    ArrayList<String> sedes =new ArrayList<String>();

    //Servicio
    ServiceSala serviceSala;
    ServiceSede serviceSede;


    //Componentes del formulario

    Button btnRegistrar;
    EditText txtNumSala,txtPisSala, txtNumAlu,txtRecuSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_registra);

        serviceSede = ConnectionRest.getConnection().create(ServiceSede.class);
        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);

        //Para el adaptador
        SalaAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,sedes);
        spnSede = findViewById(R.id.spnRegSedeSala);
        spnSede.setAdapter(SalaAdapter);

        cargaSede();

        txtNumSala = findViewById(R.id.txtRegNumeroDeSala);
        txtPisSala = findViewById(R.id.txtRegPisoSala);
        txtNumAlu = findViewById(R.id.txtRegNumeroDeAlumnos);
        txtRecuSala = findViewById(R.id.txtRegRecursoSala);
        btnRegistrar = findViewById(R.id.btnRegRegistrar);
        Locale.setDefault(new Locale("es_ESP"));
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todos los recibimos como String
                String numSal = txtNumSala.getText().toString();
                String pisSal = txtPisSala.getText().toString();
                String numAlu = txtNumAlu.getText().toString();
                String recSal = txtRecuSala.getText().toString();
                if(!numSal.matches(ValidacionUtil.TEXTO)){
                    txtNumSala.setError("El numero de sala debe estar correcto");
                }else if (!pisSal.matches(ValidacionUtil.EDAD)){
                    txtPisSala.setError("El piso de sala debe estar correcto");
                }else if(!numAlu.matches(ValidacionUtil.EDAD)){
                    txtNumAlu.setError("El numero de Alumno debe estar correcto");
                }else if(!recSal.matches(ValidacionUtil.TEXTO)){
                    txtRecuSala.setError("El recurso sala es de 2 a 10 caracteres");
                }else{
                    String sede = spnSede.getSelectedItem().toString();
                    String idSede = sede.split(":")[0];
                    Sede objSede = new Sede();
                    objSede.setIdSede(Integer.parseInt(idSede));

                    Sala objSala = new Sala();
                    objSala.setNumero(numSal);
                    objSala.setPiso(Integer.parseInt(pisSal));
                    objSala.setNumAlumnos(Integer.parseInt(numAlu));
                    objSala.setRecursos(recSal);
                    objSala.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objSala.setEstado(1);
                    objSala.setSede(objSede);

                    insertaSala(objSala);

                }
            }
        });
    }

    public void insertaSala(Sala objSala){
        Call<Sala> call = serviceSala.insertaSala(objSala);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if(response.isSuccessful()){
                    Sala objSalida = response.body();
                    mensajeAlert("Registro exitoso >>>>> ID >>" + objSalida.getIdSala()
                    +">>> Numero de Sala >>>>" + objSalida.getNumero());
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

public void cargaSede(){
    Call<List<Sede>> call =serviceSede.listaSede();
    call.enqueue(new Callback<List<Sede>>() {
        @Override
        public void onResponse(Call<List<Sede>> call, Response<List<Sede>> response) {
            if(response.isSuccessful()){
                List<Sede>lstSedes = response.body();
                for(Sede obj: lstSedes){
                    sedes.add(obj.getIdSede()+":"+obj.getNombre());
                }
                SalaAdapter.notifyDataSetChanged();
            }else{
                mensajeToast("Error al acceder al Servicio Rest >>> ");
            }
        }

        @Override
        public void onFailure(Call<List<Sede>> call, Throwable t) {
            mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
        }
    });
}

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }




}