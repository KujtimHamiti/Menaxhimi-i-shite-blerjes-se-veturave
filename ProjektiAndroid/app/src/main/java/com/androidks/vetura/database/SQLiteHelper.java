package com.androidks.vetura.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.androidks.vetura.beans.Vetura;


public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, "vetura.sqlite", null, 1);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    public void insertVetura(Vetura vetura){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO VETURA VALUES (NULL,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        //SQLiteStatmentPërfaqëson një statment që mund të ekzekutohet kundrejt një baze të dhënash.
        // Statement  nuk mund të kthejë rreshta ose kolona të shumta,
        // por janë të mbështetura vlerat me vlerë të vetme (1 x 1).
        statement.bindString(1, vetura.getTitulli());
        statement.bindString(2, vetura.getViti());
        statement.bindString(3, vetura.getKilometrazha());
        statement.bindString(4, vetura.getKomuna());
        statement.bindString(5, vetura.getTel());
        statement.bindString(6, vetura.getPershkrimi());
        statement.bindString(7, vetura.getLloji());
        statement.bindBlob(8, vetura.getFoto());
        statement.bindString(9,vetura.getEmail());
        statement.executeInsert();

    }

    //leximi i te dhenave nga nje kursor
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    //me e ba update nje nje postimi
    public void updateData(Vetura vetura,int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "UPDATE VETURA SET " +
                VETURA_TITULLI + "=?," +
                VETURA_VITI + "=?," +
                VETURA_KILOMETRAZHA + "=?," +
                VETURA_KOMUNA + "=?," +
                VETURA_KONTAKTI + "=?," +
                VETURA_PERSHKRIMI + "=?," +
                VETURA_LLOJI + "=?," +
                VETURA_FOTO + "=?" +
                " where id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.bindString(1, vetura.getTitulli());
        statement.bindString(2, vetura.getViti());
        statement.bindString(3, vetura.getKilometrazha());
        statement.bindString(4, vetura.getKomuna());
        statement.bindString(5, vetura.getTel());
        statement.bindString(6, vetura.getPershkrimi());
        statement.bindString(7, vetura.getLloji());
        statement.bindBlob(8, vetura.getFoto());
        statement.bindDouble(9,id);

        statement.execute();
        sqLiteDatabase.close();
    }
    //me e fshi  qat postim
    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM VETURA WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static final String ID ="id";
    public static final String TABLE_VETURA = "VETURA";
    public static final String  VETURA_TITULLI = "titulli";
    public static final String  VETURA_VITI = "viti";
    public static final String  VETURA_KILOMETRAZHA = "kilometrazha";
    public static final String  VETURA_KOMUNA = "komuna";
    public static final String  VETURA_KONTAKTI = "kontakti";
    public static final String  VETURA_PERSHKRIMI = "pershkrimi";
    public static final String  VETURA_LLOJI = "lloji";
    public static final String  VETURA_FOTO = "foto";
    public static final String  PERDORUESI_EMAIL= "email";

    public static final String CREATE_TABLE_VETURA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_VETURA + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + VETURA_TITULLI + " VARCHAR,"
            + VETURA_VITI + " VARCHAR,"
            + VETURA_KILOMETRAZHA + " VARCHAR,"
            + VETURA_KOMUNA + " VARCHAR,"
            + VETURA_KONTAKTI + " VARCHAR," + VETURA_PERSHKRIMI + " VARCHAR," + VETURA_LLOJI + " VARCHAR,"
            + VETURA_FOTO + " BLOB," + PERDORUESI_EMAIL + " DATETIME" + ")";
}


