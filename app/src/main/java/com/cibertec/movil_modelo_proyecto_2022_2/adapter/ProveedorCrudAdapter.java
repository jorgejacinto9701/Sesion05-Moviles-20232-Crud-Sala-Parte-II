package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;

import java.util.List;

public class ProveedorCrudAdapter extends ArrayAdapter<Proveedor> {
    private Context context;
    private List<Proveedor> lista;

    public ProveedorCrudAdapter(@NonNull Context context, int resource, @NonNull List<Proveedor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_proveedor_crud_item, parent, false);

        TextView txtIdProveedor = row.findViewById(R.id.txtCrudProveedorItemId);
        TextView txtRazonSocial = row.findViewById(R.id.txtCrudProveedorItemRazonSocial);
        TextView txtRuc = row.findViewById(R.id.txtCrudProveedorItemRuc);
        TextView txtDireccion = row.findViewById(R.id.txtCrudProveedorItemDireccion);
        /*TextView txtTelefono = row.findViewById(R.id.txtCrudProveedorItemTelefono);
        TextView txtCelular = row.findViewById(R.id.txtCrudProveedorItemCelular);
        TextView txtContacto = row.findViewById(R.id.txtCrudProveedorItemContacto);
        TextView txtEstado = row.findViewById(R.id.txtCrudProveedorItemEstado);
        TextView txtFechaRegistro = row.findViewById(R.id.txtCrudProveedorItemFechaRegistro);
        TextView txtPais = row.findViewById(R.id.txtCrudProveedorItemPais);
        TextView txtTipoProveedor = row.findViewById(R.id.txtCrudProveedorItemTipoProveedor);*/


        Proveedor objProveedor = lista.get(position);
        txtIdProveedor.setText(String.valueOf(objProveedor.getIdProveedor()));
        txtRazonSocial.setText(objProveedor.getRazonsocial());
        txtRuc.setText(objProveedor.getRuc());
        txtDireccion.setText(objProveedor.getDireccion());
        /*txtTelefono.setText(objProveedor.getTelefono());
        txtCelular.setText(objProveedor.getCelular());
        txtContacto.setText(objProveedor.getContacto());
        txtEstado.setText(String.valueOf(objProveedor.getEstado()));
        txtFechaRegistro.setText(objProveedor.getFechaRegistro());
        txtPais.setText(objProveedor.getPais().getNombre());
        txtTipoProveedor.setText(objProveedor.getTipoProveedor().getDescripcion());*/

        return row;
    }
}
