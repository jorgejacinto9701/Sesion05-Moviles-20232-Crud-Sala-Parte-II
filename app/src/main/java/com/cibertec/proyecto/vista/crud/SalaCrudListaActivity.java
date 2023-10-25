package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.util.NewAppCompatActivity;


public class SalaCrudListaActivity extends NewAppCompatActivity {

    Button btnLista;
    Button btnRegistra;
    ListView lstLista;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_lista);

        btnLista = findViewById(R.id.idCrudSalaLista);
        btnRegistra = findViewById(R.id.idCrudSalaRegistra);
        lstLista = findViewById(R.id.idCrudSalaListView);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SalaCrudListaActivity.this,
                                      SalaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRAR");
                startActivity(intent);
            }
        });


    }





}