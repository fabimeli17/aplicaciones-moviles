package com.example.experttire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.experttire.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificacionesActivity extends AppCompatActivity {


    private List<NotificacionBean> notificaciones = new ArrayList<NotificacionBean>();
    private RecyclerView recyclerView;
    private NotificacionesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new NotificacionesAdapter(notificaciones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(mAdapter);

        prepareNotificacionesData();

    }


    private void prepareNotificacionesData() {

        NotificacionBean notificacion;
        Calendar calendar = Calendar.getInstance();
        Date fecha = new Date();

        for(int i = 1; i <= 20; i++)
        {
            calendar.setTime(fecha);
            calendar.add(Calendar.DAY_OF_YEAR, i);
            notificacion = new NotificacionBean(i, "Notificacion "+i,
                    "Descripcion de Notificacion "+i, 1, "01/01/2019", "Activo");
            notificaciones.add(notificacion);
        }


        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }


    Intent intent;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_bienvenida:
                //showHelp();
                intent = new Intent(this, BienvenidaActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_bienvenida...");
                return true;

            case R.id.menu_punto_venta:
                //newGame();
                intent = new Intent(this, UbicarPuntoVentaActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_punto_venta...");
                return true;

            case R.id.menu_contactenos:
                //newGame();
                intent = new Intent(this, ContactanosActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_contactenos...");
                return true;

            case R.id.menu_foto_pedido:
                //newGame();
                intent = new Intent(this, FotoPedidoActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_foto_pedido...");
                return true;

            case R.id.menu_notificaciones:
                intent = new Intent(this, NotificacionesActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_notificaciones...");
                return true;

            case R.id.menu_cerrar:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_cerrar...");
                return true;

            case R.id.menu_preferencias:
                intent = new Intent(this, PreferenciasActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_preferencias...");
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
