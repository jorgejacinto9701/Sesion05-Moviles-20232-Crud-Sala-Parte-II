package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class AutorConsultaDetalleActivity extends NewAppCompatActivity {
    TextView txtidAutor, txtnombresAutor,txtapellidosAutor, txtcorreoAutor, txtfechaNacAutor;
    TextView txtcelularAutor, txtfechRegisAutor, txtestadoAutor, txtidGradoAutor,txtdescripGradoAutor,txtidpaisAutor,txtisoPaisAutor,txtnombrePaisAutor;

    Button btnregresarAutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_consulta_detalle);

        txtidAutor = findViewById(R.id.txtDetalleIdAutor);
        txtnombresAutor=findViewById(R.id.txtDetalleNombreAutor);
        txtapellidosAutor=findViewById(R.id.txtDetalleApellidoAutor);
        txtcorreoAutor=findViewById(R.id.txtDetalleCorreoAutor);
        txtfechaNacAutor=findViewById(R.id.txtDetalleFechaNacimientoAutor);
        txtcelularAutor=findViewById(R.id.txtDetalleTelefonoAutor);
        txtfechRegisAutor=findViewById(R.id.txtDetalleFechaRegistroAutor);
        txtestadoAutor=findViewById(R.id.txtDetalleEstadoAutor);
        txtidGradoAutor=findViewById(R.id.txtDetalleIdgradoAutor);
        txtdescripGradoAutor=findViewById(R.id.txtDetalleGradoDescripcionAutor);
        txtidpaisAutor=findViewById(R.id.txtDetalleIdPaisAutor);
        txtisoPaisAutor=findViewById(R.id.txtDetallePaisIsoAutor);
        txtnombrePaisAutor=findViewById(R.id.txtDetallePaisNombreAutor);
        btnregresarAutor=findViewById(R.id.btnDetalleAutorRegresar);
        Bundle extras = getIntent().getExtras();
        Autor objAutor = (Autor) extras.get("VAR_OBJETO");

        txtidAutor.setText("ID Autor:     "+Integer.toString(objAutor.getIdAutor()));
        txtnombresAutor.setText("Nombres:      "+objAutor.getNombres());
        txtapellidosAutor.setText("Apellidos:   " +objAutor.getApellidos());
        txtcorreoAutor.setText("Correo :       "+objAutor.getCorreo());
        txtfechaNacAutor.setText("Fecha Nacimiento:  " +objAutor.getFechaNacimiento());
        txtcelularAutor.setText("Teléfono:    "+ objAutor.getTelefono());
        txtfechRegisAutor.setText("Fecha de Registro:    "+ objAutor.getFechaRegistro());
        txtestadoAutor.setText("Estado:   "+Integer.toString(objAutor.getEstado()));
        txtidGradoAutor.setText("Id de grado:  "+Integer.toString(objAutor.getGrado().getIdGrado()));
        txtdescripGradoAutor.setText("Nombre del Grado:"  +objAutor.getGrado().getDescripcion());
        txtidpaisAutor.setText("Id del País:  " +Integer.toString(objAutor.getPais().getIdPais()));
        txtisoPaisAutor.setText("Iso-País:  "  +objAutor.getPais().getIso());
        txtnombrePaisAutor.setText("Nombre del País: " +objAutor.getPais().getNombre());

        btnregresarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutorConsultaDetalleActivity.this,AutorConsultaActivity.class);
                startActivity(intent);
            }

        });
    }
}