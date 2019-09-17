package com.example.experttirenotificaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void enviar(View view) {

        EditText textPreferencias = (EditText) findViewById(R.id.llanta);
        String preferencias = textPreferencias.getText().toString();

        EditText textTitulo = (EditText) findViewById(R.id.titulo);
        String titulo = textTitulo.getText().toString();

        EditText textContenido = (EditText) findViewById(R.id.contenido);
        String contenido = textContenido.getText().toString();

        ArrayList lista = (ArrayList) listarTokenPorPreferencia(preferencias);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i<lista.size(); i++) {
            enviarServicio(titulo, contenido,(String) lista.get(i));
        }


    }

    public void enviarServicio(String titulo, String contenido, String token){
        OkHttpClient client = new OkHttpClient();


        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        try {
            obj2.put("body",contenido);
            obj2.put("title",titulo);
            obj.put("to", token);
            obj.put("notification", obj2);
        } catch (JSONException e){
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), obj.toString());

/*
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart()
                .addFormDataPart("dispositivo", "")
                .build();
*/


        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .addHeader("Authorization", "key=AIzaSyBb4zET5_FoP0b-rVwITMpHoH7oNqCZW0o")
                .addHeader("Content-Type","application/json")
                .post(body)
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

    public List<String> listarTokenPorPreferencia(String preferencia){

        OkHttpClient client = new OkHttpClient();
        final List<String> lista = new ArrayList<String>();
        Request request = new Request.Builder()
                .url("http://experttire.atwebpages.com/dispositivoPreferenciaService.php/listarTokenPreferencia/"+preferencia)
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
                        Log.i("====>", (String) x.get("token"));
                        lista.add((String) x.get("token"));
                    }

                }
            }
        });

        return lista;
    }



}
