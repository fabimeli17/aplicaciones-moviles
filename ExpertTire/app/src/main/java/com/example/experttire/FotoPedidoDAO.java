package com.example.experttire;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


public class FotoPedidoDAO {

    private DbHelper _dbHelper;

    public FotoPedidoDAO(Context c) {
        _dbHelper = new DbHelper(c);
    }

    public void insertar(byte[] foto, String fecha, String usuario, String comentarios) throws DAOException {
        Log.i("FotoPedidoDAO", "insertar()");

        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            Object[] args = new Object[]{foto, fecha, usuario, comentarios, "Activo"};
            db.execSQL("INSERT INTO pedidosFoto (foto, fecha, usuario, comentarios, estado) VALUES(?,?,?,?,?)", args);
            Log.i("FotoPedidoDAO", "Se insertó");
        } catch (Exception e) {
            throw new DAOException("FotoPedidoDAO: Error al insertar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public FotoPedidoBean obtenerPorUsuario(Integer usuarioParametro) throws DAOException {
        Log.i("FotoPedidoDAO", "obtener()");
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        FotoPedidoBean modelo = new FotoPedidoBean();
        try {
            Cursor c = db.rawQuery(
                    "select codigo, foto, fecha, usuario, comentarios, estado " +
                            "from pedidosFoto " +
                            "where usuario like '%"+usuarioParametro+"%'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Integer codigo = c.getInt(c.getColumnIndex("codigo"));
                    byte[] foto = c.getBlob(c.getColumnIndex("foto"));
                    String fecha = c.getString(c.getColumnIndex("fecha"));
                    Integer usuario = c.getInt(c.getColumnIndex("usuario"));
                    String comentarios = c.getString(c.getColumnIndex("comentarios"));
                    String estado = c.getString(c.getColumnIndex("estado"));

                    modelo.setCodigo(codigo);
                    modelo.setFoto(foto);
                    modelo.setFecha(new Date(fecha));
                    modelo.setUsuario(usuario);
                    modelo.setComentarios(comentarios);
                    modelo.setEstado(estado);

                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOException("FotoPedidoDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return modelo;
    }


/*
    public LocalesBean obtener() throws DAOException {
        Log.i("LocalesDAO", "obtener()");
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        LocalesBean modelo = new LocalesBean();
        try {
            Cursor c = db.rawQuery("select codigo, descripcion, latitud, longitud, telefono, estado from locales", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Integer codigo = c.getInt(c.getColumnIndex("codigo"));
                    String descripcion = c.getString(c.getColumnIndex("descripcion"));
                    Double longitud = c.getDouble(c.getColumnIndex("longitud"));
                    Double latitud = c.getDouble(c.getColumnIndex("latitud"));
                    String telefono = c.getString(c.getColumnIndex("telefono"));
                    String estado = c.getString(c.getColumnIndex("estado"));

                    modelo.setCodigo(codigo);
                    modelo.setDescripcion(descripcion);
                    modelo.setLatitud(latitud);
                    modelo.setLongitud(longitud);
                    modelo.setTelefono(telefono);
                    modelo.setEstado(estado);

                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOException("LocalesDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return modelo;
    }

    public ArrayList<LocalesBean> obtenerLista() throws DAOException {
        Log.i("LocalesDAO", "obtener()");
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<LocalesBean> lista = new ArrayList<>();
        try {
            Cursor c = db.rawQuery("select codigo, descripcion, latitud, longitud, telefono, estado from locales", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Integer codigo = c.getInt(c.getColumnIndex("codigo"));
                    String descripcion = c.getString(c.getColumnIndex("descripcion"));
                    Double longitud = c.getDouble(c.getColumnIndex("longitud"));
                    Double latitud = c.getDouble(c.getColumnIndex("latitud"));
                    String telefono = c.getString(c.getColumnIndex("telefono"));
                    String estado = c.getString(c.getColumnIndex("estado"));
                    LocalesBean modelo = new LocalesBean();
                    modelo.setCodigo(codigo);
                    modelo.setDescripcion(descripcion);
                    modelo.setLatitud(latitud);
                    modelo.setLongitud(longitud);
                    modelo.setTelefono(telefono);
                    modelo.setEstado(estado);
                    lista.add(modelo);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOException("LocalesDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return lista;
    }

    public ArrayList<LocalesBean> buscar(String criterio) throws DAOException {
        Log.i("LocalesDAO", "buscar()");
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        ArrayList<LocalesBean> lista = new ArrayList<LocalesBean>();
        try {
            Cursor c = db.rawQuery(
                    "select codigo, descripcion, latitud, longitud, telefono, estado " +
                            "from locales where descripcion like '%"+criterio+"%' ", null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Integer codigo = c.getInt(c.getColumnIndex("codigo"));
                    String descripcion = c.getString(c.getColumnIndex("descripcion"));
                    Double longitud = c.getDouble(c.getColumnIndex("longitud"));
                    Double latitud = c.getDouble(c.getColumnIndex("latitud"));
                    String telefono = c.getString(c.getColumnIndex("telefono"));
                    String estado = c.getString(c.getColumnIndex("estado"));

                    LocalesBean modelo = new LocalesBean();
                    modelo.setCodigo(codigo);
                    modelo.setDescripcion(descripcion);
                    modelo.setLatitud(latitud);
                    modelo.setLongitud(longitud);
                    modelo.setTelefono(telefono);
                    modelo.setEstado(estado);

                    lista.add(modelo);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOException("LocalesDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return lista;
    }

    public void eliminar(int codigo) throws DAOException {
        Log.i("LocalesDAO", "eliminar()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            String[] args = new String[]{String.valueOf(codigo)};
            db.execSQL("DELETE FROM locales WHERE codigo=?", args);
        } catch (Exception e) {
            throw new DAOException("LocalesDAO: Error al eliminar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void eliminarTodos() throws DAOException {
        Log.i("LocalesDAO", "eliminarTodos()");
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM locales");
        } catch (Exception e) {
            throw new DAOException("LocalesDAO: Error al eliminar todos: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
*/

}
