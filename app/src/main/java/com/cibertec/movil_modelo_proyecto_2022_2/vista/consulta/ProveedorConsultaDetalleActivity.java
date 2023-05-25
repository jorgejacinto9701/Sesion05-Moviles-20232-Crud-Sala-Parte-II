package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

public class ProveedorConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleContacto, txtDetalleTelefono, txtDetalleCelular;
    TextView txtDetalleEstado, txtDetallePais, txtDetalleTipoProveedor;
    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta_detalle);

        txtDetalleContacto = findViewById(R.id.txtDetalleContacto);
        txtDetalleTelefono = findViewById(R.id.txtDetalleTelefono);
        txtDetalleCelular = findViewById(R.id.txtDetalleCelular);
        txtDetalleEstado = findViewById(R.id.txtDetalleEstado);
        txtDetallePais = findViewById(R.id.txtDetallePais);
        txtDetalleTipoProveedor = findViewById(R.id.txtDetalleTipoProveedor);
        btnRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Proveedor objProveedor = (Proveedor) extras.get("VAR_OBJETO");

        txtDetalleContacto.setText(objProveedor.getContacto());
        txtDetalleTelefono.setText("Teléfono : " + objProveedor.getTelefono());
        txtDetalleCelular.setText("Célular : " + objProveedor.getCelular());
        txtDetalleEstado.setText("Estado : " + String.valueOf(objProveedor.getEstado()));
        txtDetallePais.setText("País : " + objProveedor.getPais().getNombre());
        txtDetalleTipoProveedor.setText("Proveedor : " + objProveedor.getTipoProveedor().getDescripcion());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProveedorConsultaDetalleActivity.this, ProveedorConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}