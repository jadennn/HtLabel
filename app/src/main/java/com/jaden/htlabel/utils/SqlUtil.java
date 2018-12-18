package com.jaden.htlabel.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created Date: 2018/12/6
 * Description: 数据库工具，用来存储历史label
 */
public class SqlUtil {
    private static final String DB_NAME = "tricolor_north.db";
    private static final String TABLE = "tricolor_list";
    private static final String ID = "_id";

    public static final String LABEL_LIST = "label_list"; //标签列表

    private static final String CREATE_SQL = "CREATE TABLE " + TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LABEL_LIST + " TEXT"
            + ")";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE + "(" + LABEL_LIST + ")"
            + "VALUES(" + "\""  +"\"" +")";

    private volatile static SqlUtil sManager;
    private InnerSqlOpenHelper mSqlOpenHelper;
    private SQLiteDatabase mDB;

    private SqlUtil() {
    }

    public static SqlUtil get() {
        if (sManager == null) {
            synchronized (SqlUtil.class) {
                if(sManager == null) {
                    sManager = new SqlUtil();
                }
            }
        }
        return sManager;
    }

    public void init(Context context) {
        mSqlOpenHelper = new InnerSqlOpenHelper(context, DB_NAME, null, 1);
        mDB = mSqlOpenHelper.getReadableDatabase();
    }

    public void deInit(){
        mDB.close();
        mSqlOpenHelper = null;
        sManager = null;
    }

    public void delete(){
        mDB.delete(TABLE, null, null);
    }

    public void saveObject(Object object, String column) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            mDB.execSQL("UPDATE " + TABLE +  " SET " + column + "  = ?", new Object[] { data });
        } catch (Exception e) {
            Slog.e(e.toString());
        }
    }

    public Object getObject(String column) {
        Object object = null;
        Cursor cursor = mDB.rawQuery("select * from " + TABLE , null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex(column));
                try {
                    ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    object = inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return object;
    }

    public class InnerSqlOpenHelper extends SQLiteOpenHelper {
        private InnerSqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Slog.i("onCreate ");
            db.execSQL(CREATE_SQL);
            db.execSQL(INSERT_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Slog.e("sqlite upgrade from " + oldVersion + " to " + newVersion);
        }
    }
}
