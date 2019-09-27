package com.example.experttire;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.experttire.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FotoPedidoActivity extends AppCompatActivity {



    private ImageView miImagen;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_pedido);
        miImagen = findViewById(R.id.foto);

        /*
        FotoPedidoBean fotoPedidoBean = obtenerFotoPedidoPorUsuario(1);
        Log.i("FotoPedidoActivity", "====> " + fotoPedidoBean.getComentarios());
        byte[] bArray = fotoPedidoBean.getFoto();
        Log.i("FotoPedidoActivity", "====> " + bArray.toString());
        Bitmap bmp = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
        miImagen.setImageBitmap(
                Bitmap.createScaledBitmap(bmp, 250,
                250, false));

         */


    }

    public void tomarFoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imagenBit = (Bitmap) extras.get("data");
            miImagen.setImageBitmap(imagenBit);
        }
    }

    public void solicitarPedidoFoto(View view) {

        miImagen = findViewById(R.id.foto);
        Bitmap photo = ((BitmapDrawable)miImagen.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //Log.i("====>", bos.toString());
        photo.compress(Bitmap.CompressFormat.PNG, 100, bos);

        byte[] bArray = bos.toByteArray();

        String fecha = (new Date()).toString();
        EditText textComentarios = (EditText) findViewById(R.id.comentarios);
        String comentarios = textComentarios.getText().toString();

        FotoPedidoDAO dao = new FotoPedidoDAO(getBaseContext());

        Integer usuario = ((Globales) this.getApplication()).getUsuario_codigo();
        grabar(bArray, fecha, usuario.toString(),comentarios);
        Toast toast= Toast.makeText(getApplicationContext(), "Se insertó correctamente", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        textComentarios.setText("");


    }

    private void grabar(byte[] bArray, String fecha, String usuario, String comentarios)  {

        OkHttpClient client = new OkHttpClient();
        String foto = "";
         foto = Base64.getEncoder().encodeToString(bArray);
        Log.i("====>", foto);
        Log.i("====>", String.valueOf(bArray[0]));

        //byte[] arreglo = Base64.getDecoder().decode(foto);
        //Log.i("====>", String.valueOf(arreglo[0]));
        //Log.i("====>", String.valueOf(arreglo[0]));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("foto", foto)
                .addFormDataPart("fecha", fecha)
                .addFormDataPart("usuario", usuario)
                .addFormDataPart("comentarios", comentarios)
                .build();

        Request request = new Request.Builder()
                .url("http://experttire.atwebpages.com/fotoPedidoService.php/insertar")
                //.url("http://localhost:8080/grabarFoto")
                //.url("http://desktop-sv7oa86:8080/grabarFoto")
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



    private FotoPedidoBean obtenerFotoPedidoPorUsuario(Integer usuario){
        FotoPedidoBean fotoPedidoBean = new FotoPedidoBean();

        FotoPedidoDAO dao = new FotoPedidoDAO(getBaseContext());
        try {
            fotoPedidoBean = dao.obtenerPorUsuario(usuario);
        } catch (DAOException e) {
            Log.i("FotoPedidoActivity", "====> " + e.getMessage());
        }
        return  fotoPedidoBean;
    }

    /*
    private Button btnCapture;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCapture =(Button)findViewById(R.id.btnFotoPedido);
        imgCapture = (ImageView) findViewById(R.id.imagen);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
*/

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
