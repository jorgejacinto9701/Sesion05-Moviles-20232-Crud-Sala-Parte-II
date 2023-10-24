package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.EditorialAdapter;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;

public class EditorialConsultaActivity extends NewAppCompatActivity {

    Button btnListar;
    ListView lstEditorial;
    ArrayList<Editorial> data = new ArrayList<>();
    EditorialAdapter adapter;
    ServiceEditorial serviceEditorial;
    EditText txtFiltro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editorial_consulta);
    }



}