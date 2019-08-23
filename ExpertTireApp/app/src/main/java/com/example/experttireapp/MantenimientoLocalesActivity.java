package com.example.experttireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.experttireapp.DAOException;

public class MantenimientoLocalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_locales);
    }

    public void grabar(View view) {

        EditText codigo = (EditText) findViewById(R.id.codigo);
        EditText descripcion = (EditText) findViewById(R.id.descripcion);
        EditText latitud = (EditText) findViewById(R.id.latitud);
        EditText longitud = (EditText) findViewById(R.id.longitud);
        EditText telefono = (EditText) findViewById(R.id.telefono);


        LocalesDAO dao = new LocalesDAO(getBaseContext());
        try {
            //dao.eliminarTodos();
            //dao.insertar(descripcion.getText().toString());
            dao.insertar(
                    Integer.parseInt(codigo.getText().toString()),
                    descripcion.getText().toString(),
                    Double.parseDouble(latitud.getText().toString()),
                    Double.parseDouble(longitud.getText().toString()),
                    telefono.getText().toString()
                    );
            Toast toast= Toast.makeText(getApplicationContext(), "Se insertó correctamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

            codigo.setText("");
            descripcion.setText("");
            latitud.setText("");
            longitud.setText("");
            telefono.setText("");
        } catch (DAOException e) {
            Log.i("ManteLocalesActivity", "====> " + e.getMessage());
        }
    }


}
