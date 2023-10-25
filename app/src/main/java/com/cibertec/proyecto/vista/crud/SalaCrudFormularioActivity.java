package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.util.NewAppCompatActivity;

public class SalaCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_formulario);

        btnRegresar = findViewById(R.id.btnCrudSalaRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SalaCrudFormularioActivity.this,
                        SalaCrudListaActivity.class);
                  startActivity(intent);

            }
        });

    }



}