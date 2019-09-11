package com.example.experttireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
    }

    public void ingresar2 (View view) {
        Intent intentLogIn = new Intent(this, MenuSuperiorActivity.class);
        intentLogIn.putExtra("usuarioNombre","Diego Mori");
        startActivity(intentLogIn);
    }
}
