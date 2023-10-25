package com.cibertec.proyecto.util;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.vista.consulta.SalaConsultaActivity;
import com.cibertec.proyecto.vista.crud.SalaCrudListaActivity;
import com.cibertec.proyecto.vista.registra.SalaRegistraActivity;

public class NewAppCompatActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.idMenuRegSala){
            Intent intent = new Intent(this, SalaRegistraActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.idMenuCrudSala){
            Intent intent = new Intent(this, SalaCrudListaActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.idMenuConsultaSala){
            Intent intent = new Intent(this, SalaConsultaActivity.class);
            startActivity(intent);
            return true;
        }
       

        return super.onOptionsItemSelected(item);
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void mensajeToastLong(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeToastShort(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }



}
