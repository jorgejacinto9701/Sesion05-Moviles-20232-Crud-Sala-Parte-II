package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

public class EditorialConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleIdEditorial, txtDetalleRazonSocial;
    TextView txtDetalleDireccionEditorial, txtDetalleRucEditorial, txtDetallePaisEditorial, txtDetalleCategoriaEditorial;

    Button btnDetalleVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_consulta_detalle);

        txtDetalleIdEditorial = findViewById(R.id.txtDetalleIdEditorial);
        txtDetalleRazonSocial = findViewById(R.id.txtDetalleRazonSocial);
        txtDetalleDireccionEditorial = findViewById(R.id.txtDetalleDireccionEditorial);
        txtDetalleRucEditorial = findViewById(R.id.txtDetalleRucEditorial);
        txtDetallePaisEditorial = findViewById(R.id.txtDetallePaisEditorial);
        txtDetalleCategoriaEditorial = findViewById(R.id.txtDetalleCategoriaEditorial);


        Bundle extras = getIntent().getExtras();
        Editorial objEditorial = (Editorial) extras.get("Var_Objeto");

        txtDetalleIdEditorial.setText(objEditorial.getIdEditorial());
        txtDetalleRazonSocial.setText(objEditorial.getRazonSocial());
        txtDetalleDireccionEditorial.setText("Dirección: " + objEditorial.getDireccion());
        txtDetalleRucEditorial.setText("RUC: " + objEditorial.getRuc());
        txtDetallePaisEditorial.setText("Pais: " + objEditorial.getPais().getNombre());
        txtDetalleCategoriaEditorial.setText("Categoria: " + objEditorial.getCategoria().getDescripcion());

        btnDetalleVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditorialConsultaDetalleActivity.this, EditorialConsultaActivity.class);
                startActivity(intent);
            }
        });

    }
}