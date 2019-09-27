package com.example.experttire;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class FotoPerfilDAO {

    private DbHelper _dbHelper;

    public FotoPerfilDAO(Context context) {

        _dbHelper = new DbHelper(context);
    }

    public FotoPerfilBean obtenerFoto() throws DAOException {

        Log.i("FotoPerfilDAO", "obtenerFoto()");

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        FotoPerfilBean fotoPerfilBean = new FotoPerfilBean();

        try {

            Cursor c = db.rawQuery("select codigo, foto, fecha from fotosPerfil", null);

            if (c.getCount() > 0) {

                c.moveToFirst();

                do {

                    Integer codigo = c.getInt(c.getColumnIndex("codigo"));
                    byte[] foto = c.getBlob(c.getColumnIndex("foto"));
                    String fecha = c.getString(c.getColumnIndex("fecha"));

                    fotoPerfilBean.setCodigo(codigo);
                    fotoPerfilBean.setFoto(foto);
                    //fotoPerfilBean.setFecha(new Date(fecha));

                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
            throw new DAOException("FotoPerfilDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return fotoPerfilBean;
    }

    public void insertarFoto(byte[] foto, String fecha) throws DAOException {

        Log.i("FotoPerfilDAO", "insertarFoto()");

        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {

            Object[] args = new Object[]{foto, fecha};
            db.execSQL("INSERT INTO fotosPerfil (foto, fecha) VALUES(?,?)", args);
            Log.i("FotoPerfilDAO", "Se insertó");

        } catch (Exception e) {
            throw new DAOException("FotoPerfilDAO: Error al insertar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void eliminarFoto() throws DAOException {
        Log.i("FotoPerfilDAO", "eliminarFoto()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM fotosPerfil");
        } catch (Exception e) {
            throw new DAOException("FotoPerfilDAO: Error al eliminar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}
