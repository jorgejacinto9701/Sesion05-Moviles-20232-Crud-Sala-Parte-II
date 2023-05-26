package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

public class RevistaConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleNombreRevista,txtDetalleIdRevista;

    TextView txtDetalleFrecuencia,txtDetalleFechaCreacion,txtDetalleFechaRegistro,txtDetalleEstado,txtDetalleModalidad,txtDetallePais;

    Button btnRegresar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_consulta_detalle);

        txtDetalleIdRevista = findViewById(R.id.txtDetalleIdRevista);
        txtDetalleNombreRevista = findViewById(R.id.txtDetalleNombreRevista);
        txtDetalleFrecuencia = findViewById(R.id.txtDetalleFrecuencia);
        txtDetalleFechaCreacion = findViewById(R.id.txtDetalleFechaCreacion);
        txtDetalleFechaRegistro= findViewById(R.id.txtDetalleFechaRegistro);
        txtDetalleEstado = findViewById(R.id.txtDetalleEstado);
        txtDetalleModalidad = findViewById(R.id.txtDetalleModalidad);
        txtDetallePais= findViewById(R.id.txtDetallePais);
        btnRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Revista objRevista = (Revista) extras.get("VAR_OBJETO");

        txtDetalleNombreRevista.setText("Nombre : " + objRevista.getNombre());
        txtDetalleIdRevista.setText("ID : " + objRevista.getIdRevista());
        txtDetalleFrecuencia.setText("Frecuencia : " + objRevista.getFrecuencia());
        txtDetalleFechaCreacion.setText("Fecha de Creación : " + objRevista.getFechaCreacion());
        txtDetalleFechaRegistro.setText("Fecha de Registro : " + objRevista.getFechaRegistro());
        txtDetalleEstado.setText("Estado : " +objRevista.getEstado());
        txtDetalleModalidad.setText("Modalidad : " +objRevista.getModalidad().getDescripcion());
        txtDetallePais.setText("País :" + objRevista.getPais().getNombre());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RevistaConsultaDetalleActivity.this,RevistaConsultaActivity.class);
                startActivity(intent);

            }
        });
    }
}