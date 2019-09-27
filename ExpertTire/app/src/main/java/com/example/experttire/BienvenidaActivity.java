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
import android.widget.TextView;
import android.widget.Toast;

import com.example.experttire.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class BienvenidaActivity extends AppCompatActivity {

    private ImageView miImagen;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        Bundle bundle = getIntent().getExtras();
        //String correo=bundle.getString("correo");
        String correo = ((Globales) this.getApplication()).getUsuario_correo();
        miImagen = findViewById(R.id.foto_perfil);

        FotoPerfilDAO fotoPerfilDAO = new FotoPerfilDAO(getBaseContext());
        FotoPerfilBean fotoPerfilBean = obtenerFotoPerfil();

        if (fotoPerfilBean.getCodigo()!=null) {

            byte[] bArray = fotoPerfilBean.getFoto();
            Log.i("BienvenidaActivity", "====> " + bArray.toString()==null?"":bArray.toString());

            Bitmap bmp = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
            miImagen.setImageBitmap(
                    Bitmap.createScaledBitmap(bmp, 250,
                            250, false));
        }

        final TextView textoBienvenida = (TextView) findViewById(R.id.nombre);
        textoBienvenida.setText("Bienvenido " + correo);

    }

    private FotoPerfilBean obtenerFotoPerfil(){

        FotoPerfilBean fotoPerfilBean = new FotoPerfilBean();
        FotoPerfilDAO fotoPerfilDAO = new FotoPerfilDAO(getBaseContext());

        try {
            fotoPerfilBean = fotoPerfilDAO.obtenerFoto();
        } catch (DAOException e) {
            Log.i("BienvenidaActivity", "====> " + e.getMessage());
        }
        return  fotoPerfilBean;
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

    public void cambiarFoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagenBit = (Bitmap) extras.get("data");
            miImagen.setImageBitmap(imagenBit);

            Bitmap photo = ((BitmapDrawable)miImagen.getDrawable()).getBitmap();
            OutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);

            byte[] bArray = ((ByteArrayOutputStream) bos).toByteArray();
            String fecha = (new Date()).toString();

            FotoPerfilDAO fotoPerfilDAO = new FotoPerfilDAO(getBaseContext());

            try {
                fotoPerfilDAO.eliminarFoto();
                fotoPerfilDAO.insertarFoto(bArray, fecha);
                Toast toast= Toast.makeText(getApplicationContext(), "Se insertó correctamente", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            } catch (DAOException e) {
                Log.i("FotoPedidoActivity", "====> " + e.getMessage());
            }

        }
    }


}
