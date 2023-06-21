package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresarEditorial, btnCrudRegistrarEditorial;
    TextView txtTitulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);

        btnCrudRegistrarEditorial = findViewById(R.id.btnCrudRegistrarEditorial);
        txtTitulo = findViewById(R.id.idCrudEditorial);
        Bundle extras = getIntent().getExtras();
        String tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        btnCrudRegistrarEditorial.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresarEditorial = findViewById(R.id.btnCrudRegresarEditorial);
        btnCrudRegresarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditorialCrudFormularioActivity.this, EditorialCrudListaActivity.class);
                startActivity(intent);
            }
        });

    }



}