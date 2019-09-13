package com.example.experttire.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String clave;
    private String correo;

    public LoggedInUser(){

    }

    public LoggedInUser(String userId, String displayName) {
        this.setUserId(userId);
        this.setDisplayName(displayName);
    }

    public LoggedInUser(String userId, String displayName, String clave, String correo){
        this.setUserId(userId);
        this.setDisplayName(displayName);
        this.setClave(clave);
        this.setCorreo(correo);
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
