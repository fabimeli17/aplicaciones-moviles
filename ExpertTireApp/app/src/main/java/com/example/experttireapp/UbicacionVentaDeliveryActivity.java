package com.example.experttireapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UbicacionVentaDeliveryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_venta_delivery);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_consultar:
                //showHelp();
                Intent intentConsultar = new Intent(this, ConsultaProductosActivity.class);
                startActivity(intentConsultar);
                Log.i("======>", "click en menu_consultar...");
                return true;
            case R.id.menu_ubicanos:
                //newGame();
                Intent intentUbicanos = new Intent(this, UbicacionVentaDeliveryActivity.class);
                startActivity(intentUbicanos);
                Log.i("======>", "click en menu_ubicanos...");
                return true;
            case R.id.menu_delivery:
                Intent intentMapa = new Intent(this, UbicacionVentaDeliveryActivity.class);
                startActivity(intentMapa);
                Log.i("======>", "click en menu_delivery...");
                return true;
            case R.id.menu_llamar:
                Intent intentLlamar = new Intent(this, LlamarTiendaActivity.class);
                startActivity(intentLlamar);
                Log.i("======>", "click en menu_llamar...");
                return true;
            case R.id.menu_pedido:
                Intent intentPedido = new Intent(this, FotoPedidoActivity.class);
                startActivity(intentPedido);
                Log.i("======>", "click en menu_pedido...");
                return true;
            case R.id.menu_comprar:
                Intent intentComprar = new Intent(this, ComprarProducto.class);
                startActivity(intentComprar);
                Log.i("======>", "click en menu_comprar...");
                return true;
            case R.id.menu_asistencia:
                Intent intentAsistencia = new Intent(this, SolicitarAsistenciaActivity.class);
                startActivity(intentAsistencia);
                Log.i("======>", "click en menu_asistencia...");
                return true;
            case R.id.menu_notificaciones:
                Intent intentNotificaciones = new Intent(this, NotificacionesActivity.class);
                startActivity(intentNotificaciones);
                Log.i("======>", "click en menu_notificaciones...");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LocalesDAO dao = new LocalesDAO(getBaseContext());
        try {
            LocalesBean local = dao.obtener();
            //Double latitud = local.getLatitud();
            //Double longitud = local.getLongitud();
            Double latitud = -12.0888719;
            Double longitud = -77.0480961;
            String descripcion = local.getDescripcion();


        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title(descripcion));

            LatLng sydney2 = new LatLng(-12.0788719, -77.0480961);
            mMap.addMarker(new MarkerOptions().position(sydney2).title(descripcion));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11F));
        } catch (DAOException e) {
            Log.i("UbicaVenDeliActi", "====> " + e.getMessage());
        }
    }
}
