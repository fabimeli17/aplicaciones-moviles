package com.example.experttire;

public class NotificacionBean {

    private Integer codigo;
    private String titulo;
    private String texto;
    private Integer usuario;
    private String fecha;
    private String estado;


    public NotificacionBean(Integer codigo, String titulo, String texto, Integer usuario, String fecha, String estado){
        this.codigo = codigo;
        this.titulo = titulo;
        this.texto = texto;
        this.usuario = usuario;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
