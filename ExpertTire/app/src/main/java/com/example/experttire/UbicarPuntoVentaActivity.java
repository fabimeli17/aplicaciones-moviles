package com.example.experttire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicarPuntoVentaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicar_punto_venta);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        Double latitud = -12.0888719;
        Double longitud = -77.0480961;
        String descripcion = "Punto de Venta 1";

        LatLng ubicacion = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(ubicacion).title(descripcion));

        Double latitud2 = -12.0788719;
        Double longitud2 = -77.0480961;
        String descripcion2 = "Punto de Venta 2";

        LatLng ubicacion2 = new LatLng(latitud2, longitud2);
        mMap.addMarker(new MarkerOptions().position(ubicacion2).title(descripcion2));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 13F));
        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */
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
                /*
            case R.id.menu_consultar:
                //showHelp();
                Intent intentConsultar = new Intent(this, ConsultaProductosActivity.class);
                startActivity(intentConsultar);
                Log.i("======>", "click en menu_consultar...");
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
             */
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
