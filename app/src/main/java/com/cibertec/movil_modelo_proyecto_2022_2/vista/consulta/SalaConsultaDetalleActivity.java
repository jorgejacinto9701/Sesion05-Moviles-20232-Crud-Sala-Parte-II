package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

public class SalaConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleNumero,txtDetallePiso;
    TextView txtDetalleNumAlumnos, txtDetalleRecurso, txtDetalleEstado,txtDetalleSede, txtDetalleModalidad;

    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta_detalle);

        txtDetalleNumero = findViewById(R.id.txtDetalleNumero);
        txtDetallePiso = findViewById(R.id.txtDetallePiso);
        txtDetalleNumAlumnos = findViewById(R.id.txtDetalleNumAlumnos);
        txtDetalleRecurso = findViewById(R.id.txtDetalleRecurso);
        txtDetalleEstado = findViewById(R.id.txtDetalleEstado);
        txtDetalleSede = findViewById(R.id.txtDetalleSede);
        txtDetalleModalidad = findViewById(R.id.txtDetalleModalidad);
        btnRegresar = findViewById(R.id.btnDetalleRegresar);



        //LOS BUNDLE
        Bundle extras = getIntent().getExtras();
        Sala objSala = (Sala)extras.get("VAR_OBJETO");


        //TODOS LOS TXTDETALLE DE LA CONSULTA SALA DETALLE
        txtDetalleNumero.setText("Numero de Sala : " + objSala.getNumero());
        txtDetallePiso.setText(String.valueOf("Numero de Piso : " +objSala.getPiso()));
        txtDetalleNumAlumnos.setText(String.valueOf("Numero de Alumnos : "+ objSala.getNumAlumnos()));
        txtDetalleRecurso.setText(String.valueOf("Recursos : "+ objSala.getRecursos()));
        txtDetalleEstado.setText(String.valueOf("Estado : " + objSala.getEstado()));
        txtDetalleSede.setText("Nombre de la Sede : " + objSala.getSede().getNombre());
        txtDetalleModalidad.setText("Modalidad : " + objSala.getModalidad().getDescripcion());




        //BOTON REGRESAR ON CLICK LISTENER
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SalaConsultaDetalleActivity.this, SalaConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}