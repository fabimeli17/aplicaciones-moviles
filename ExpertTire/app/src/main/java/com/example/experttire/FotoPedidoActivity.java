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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;


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
        OutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = ((ByteArrayOutputStream) bos).toByteArray();
        String fecha = (new Date()).toString();
        EditText textComentarios = (EditText) findViewById(R.id.comentarios);
        String comentarios = textComentarios.getText().toString();

        FotoPedidoDAO dao = new FotoPedidoDAO(getBaseContext());
        try {


            dao.insertar(
                    bArray, fecha, "1",comentarios
            );
            Toast toast= Toast.makeText(getApplicationContext(), "Se insertó correctamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            textComentarios.setText("");
        } catch (DAOException e) {
            Log.i("FotoPedidoActivity", "====> " + e.getMessage());
        }

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
