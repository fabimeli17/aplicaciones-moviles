package com.example.experttire;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.experttire.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.widget.ArrayAdapter;
import java.lang.reflect.Type;

public class PreferenciasActivity extends AppCompatActivity {

    private ListView listaPreferencias;
    private ArrayList<PreferenciasBean> modelArrayList;
    private PreferenciasAdapter customAdapter;
    private Button btnselect, btndeselect, btnnext;
    private  String[] marcaLista = new String[]{"Bridgestone", "Goodyear", "Michelin", "Firestone","Pirelli"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        listaPreferencias = (ListView) findViewById(R.id.listaPreferencias);
        btnselect = (Button) findViewById(R.id.todos);
        btndeselect = (Button) findViewById(R.id.ninguno);
        btnnext = (Button) findViewById(R.id.grabar);

        String androidId = Settings.Secure.getString (getContentResolver (),
                Settings.Secure.ANDROID_ID);

        ArrayList lista = (ArrayList) buscar(androidId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        modelArrayList = preferenciasPorDispositivo(lista);
        customAdapter = new PreferenciasAdapter(this,modelArrayList);
        listaPreferencias.setAdapter(customAdapter);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getPreferenciasBean(true);
                customAdapter = new PreferenciasAdapter(PreferenciasActivity.this,modelArrayList);
                listaPreferencias.setAdapter(customAdapter);

            }
        });
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getPreferenciasBean(false);
                customAdapter = new PreferenciasAdapter(PreferenciasActivity.this,modelArrayList);
                listaPreferencias.setAdapter(customAdapter);

            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(PreferenciasActivity.this,NextActivity.class);
                Intent intent = new Intent(PreferenciasActivity.this,PreferenciasActivity.class);
                startActivity(intent);

                String androidId = Settings.Secure.getString (getContentResolver (),
                        Settings.Secure.ANDROID_ID);

                eliminar(androidId);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < PreferenciasAdapter.modelArrayList.size(); i++){
                    if(PreferenciasAdapter.modelArrayList.get(i).isSelected()) {
                        grabar(androidId, PreferenciasAdapter.modelArrayList.get(i).getPreferencia());
                    }
                }

                ArrayList lista = (ArrayList) buscar(androidId);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                modelArrayList = preferenciasPorDispositivo(lista);
                customAdapter = new PreferenciasAdapter(PreferenciasActivity.this,modelArrayList);
                listaPreferencias.setAdapter(customAdapter);
            }

        });

    }

    private void eliminar(String dispositivo){
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://experttire.atwebpages.com/dispositivoPreferenciaService.php/eliminar/"+dispositivo)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String cadenaJson = response.body().string();
                    Log.i("====>", cadenaJson);

                    /*
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast= Toast.makeText(getApplicationContext(), "Se insertó correctamente", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                    );
                    */
                }
            }
        });
    }

    private void grabar(String dispositivo, String preferencia) {

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dispositivo", dispositivo)
                .addFormDataPart("preferencia", preferencia)
                .build();

        Request request = new Request.Builder()
                .url("http://experttire.atwebpages.com/dispositivoPreferenciaService.php/dispositivoPreferencia")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String cadenaJson = response.body().string();
                    Log.i("====>", cadenaJson);

                    /*
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast= Toast.makeText(getApplicationContext(), "Se insertó correctamente", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                    );
                    */
                }
            }
        });
    }

    public List<String> buscar(String dispositivo){

        OkHttpClient client = new OkHttpClient();
        final List<String> lista = new ArrayList<String>();
        Request request = new Request.Builder()
                .url("http://experttire.atwebpages.com/dispositivoPreferenciaService.php/listar/"+dispositivo)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String cadenaJson = response.body().string();

                    Gson gson = new Gson();
                    Type stringStringMap = new TypeToken<ArrayList<Map<String, Object>>>() { }.getType();

                    final ArrayList<Map<String, Object>> retorno = gson.fromJson(cadenaJson, stringStringMap);

                    for (Map<String, Object> x : retorno) {
                        lista.add((String) x.get("preferencia"));
                    }

                }
            }
        });

        return lista;
    }

    private ArrayList<PreferenciasBean> getPreferenciasBean(boolean isSelect){
        ArrayList<PreferenciasBean> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){

            PreferenciasBean model = new PreferenciasBean();
            model.setSelected(isSelect);
            model.setPreferencia(marcaLista[i]);
            list.add(model);
        }
        return list;
    }

    private ArrayList<PreferenciasBean> preferenciasPorDispositivo(ArrayList<String> lista){

        ArrayList<PreferenciasBean> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            PreferenciasBean model = new PreferenciasBean();
            model.setSelected(false);
            model.setPreferencia(marcaLista[i]);
            for(int j = 0; j < lista.size(); j++){
                String preferencia = new String(lista.get(j));
                if(preferencia.equals(marcaLista[i])){
                    model.setSelected(true);
                }
            }
            list.add(model);
        }
        return list;
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

            case R.id.menu_preferencias:
                intent = new Intent(this, PreferenciasActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_preferencias...");
                return true;

            case R.id.menu_cerrar:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                Log.i("======>", "click en menu_cerrar...");
                return true;

            /*
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

             */
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
