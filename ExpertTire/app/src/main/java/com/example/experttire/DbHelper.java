package com.example.experttire;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context) {
        // null porque se va a usar el SQLiteCursor
        super(context, "experttire_sql.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlLocales = "CREATE TABLE IF NOT EXISTS locales " +
                "(codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descripcion VARCHAR(200) NOT NULL, " +
                "latitud DECIMAL(4,6) NOT NULL, " +
                "longitud DECIMAL(4,6) NOT NULL, " +
                "telefono VARCHAR(9) , " +
                "estado CHAR(8) NOT NULL )";
        db.execSQL(sqlLocales);

        String sqlPedidos = "CREATE TABLE IF NOT EXISTS pedidosFoto " +
                "(codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "foto LONGBLOB, " +
                "fecha DATETIME, " +
                "usuario INTEGER, " +
                "comentarios VARCHAR(200), " +
                "estado CHAR(8) NOT NULL )";
        db.execSQL(sqlPedidos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS locales");
        db.execSQL("DROP TABLE IF EXISTS pedidosFoto");
        onCreate(db);
    }
}
