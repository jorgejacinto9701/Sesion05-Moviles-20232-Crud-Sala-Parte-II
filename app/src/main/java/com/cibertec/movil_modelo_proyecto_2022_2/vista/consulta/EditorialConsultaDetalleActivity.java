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

    TextView txtIdEditorial, txtRazonSocial;
    TextView txtDireccionEditorial, txtRucEditorial, txtPaisEditorial, txtCategoriaEditorial;

    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_consulta_detalle);

        txtIdEditorial = findViewById(R.id.txtIdEditorial);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccionEditorial = findViewById(R.id.txtDireccionEditorial);
        txtPaisEditorial = findViewById(R.id.txtPaisEditorial);
        txtCategoriaEditorial = findViewById(R.id.txtCategoriaEditorial);
        txtRucEditorial = findViewById(R.id.txtRucEditorial);

        Bundle extras = getIntent().getExtras();
        Editorial objEditorial = (Editorial) extras.get("Var_Objeto");

        txtIdEditorial.setText(objEditorial.getIdEditorial());
        txtRazonSocial.setText(objEditorial.getRazonSocial());
        txtDireccionEditorial.setText("Dirección: " + objEditorial.getDireccion());
        txtPaisEditorial.setText("Pais: " + objEditorial.getPais().getNombre());
        txtRucEditorial.setText("RUC: " + objEditorial.getRuc());
        txtCategoriaEditorial.setText("Categoria: " + objEditorial.getCategoria().getDescripcion());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditorialConsultaDetalleActivity.this, EditorialConsultaActivity.class);
                startActivity(intent);
            }
        });

    }
}