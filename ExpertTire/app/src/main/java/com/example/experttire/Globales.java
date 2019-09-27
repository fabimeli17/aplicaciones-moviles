package com.example.experttire;

import android.app.Application;

import java.util.ArrayList;

public class Globales extends Application {

    private String usuario_correo;
    private ArrayList<LocalesBean> listaLocales;
    private Integer usuario_codigo;


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

    public Integer getUsuario_codigo() {
        return usuario_codigo;
    }

    public void setUsuario_codigo(Integer usuario_codigo) {
        this.usuario_codigo = usuario_codigo;
    }
}
