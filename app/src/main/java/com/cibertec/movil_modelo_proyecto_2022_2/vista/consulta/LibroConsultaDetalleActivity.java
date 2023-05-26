package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

public class LibroConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtIdLibro, txtTitulo,txtSerie,txtAnio,txtFechaRegistro, txtEstado,
    txtCategoria, txtPais;

    Button btnRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta_detalle);

        txtIdLibro = findViewById(R.id.idDetalleIdTitulo);
        txtTitulo = findViewById(R.id.idDetalleTitulo);
        txtSerie = findViewById(R.id.idDetalleSerie);
        txtAnio = findViewById(R.id.idDetalleAnio);
        txtFechaRegistro = findViewById(R.id.idDetalleFechaRegistro);
        txtEstado = findViewById(R.id.idDetalleEstado);
        txtCategoria = findViewById(R.id.idDetalleCategoria);
        txtPais = findViewById(R.id.idDetallePais);
        btnRegresar = findViewById(R.id.btnListaDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Libro objLibro = (Libro) extras.get("VAR_OBJETO");

        txtIdLibro.setText("Código:   " +String.valueOf(objLibro.getIdLibro()));
        txtTitulo.setText("Titulo:   " +objLibro.getTitulo());
        txtSerie.setText("Serie :   " +objLibro.getSerie());
        txtAnio.setText(String.valueOf("Año :   " +objLibro.getAnio()));
        txtFechaRegistro.setText("Fecha Registro :   " +objLibro.getFechaRegistro());
        txtEstado.setText(String.valueOf("Estado :   " +objLibro.getEstado()));
        txtCategoria.setText(String.valueOf("DesCategoria :   " +objLibro.getCategoria().getDescripcion()));
        txtPais.setText(String.valueOf("País :   " +objLibro.getPais().getNombre()));

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibroConsultaDetalleActivity.this, LibroConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}