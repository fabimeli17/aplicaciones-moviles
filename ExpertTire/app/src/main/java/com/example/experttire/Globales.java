package com.example.experttire;

import android.app.Application;

public class Globales extends Application {

    private String usuario_correo;


    public String getUsuario_correo() {
        return usuario_correo;
    }

    public void setUsuario_correo(String usuario_correo) {
        this.usuario_correo = usuario_correo;
    }
}
