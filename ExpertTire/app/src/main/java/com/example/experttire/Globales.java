package com.example.experttire;

import android.app.Application;

import java.util.ArrayList;

public class Globales extends Application {

    private String usuario_correo;
    private ArrayList<LocalesBean> listaLocales;


    public String getUsuario_correo() {
        return usuario_correo;
    }

    public void setUsuario_correo(String usuario_correo) {
        this.usuario_correo = usuario_correo;
    }


    public ArrayList<LocalesBean> getListaLocales() {
        return listaLocales;
    }

    public void setListaLocales(ArrayList<LocalesBean> listaLocales) {
        this.listaLocales = listaLocales;
    }
}
