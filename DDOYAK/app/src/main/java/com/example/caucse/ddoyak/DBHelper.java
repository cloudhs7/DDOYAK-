package com.example.caucse.ddoyak;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHelper extends SQLiteOpenHelper {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference MediRef = database.getReference("DOSE");

    //내부 DB의 필드명 지정
    private static final String KEY_ID = "id";
    private static final String KEY_USERID = "user_id";
    private static final String KEY_TIME = "time";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ALARMTIMES (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, time TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "ALARMTIMES");
        onCreate(db);
    }

    //db 데이터 삽입
    public void insert(String user_id, String time){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ALARMTIMES VALUES(null,' " + user_id + "','" + time + "');");
        db.close();
    }

    //id에 따라 삽입
    public void update(String user_id, String time, int sqlite_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ALARMTIMES SET time = '" + time + "', user_id = '" + user_id + "' WHERE id ='" + sqlite_id + "';");
        db.close();
    }

    //삭제
    public void delete(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM ALARMTIMES;");
        db.execSQL("DELETE FROM 'sqlite_sequence' WHERE name = 'ALARMTIMES';");
        db.close();
    }

    //모든 데이터 얻어와서 띄우기
    public String getAllData(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM ALARMTIMES", null);
        //cursor를 이동하며 필드마다 값을 얻는다.
        while(cursor.moveToNext()){
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " : "
                    + cursor.getString(2)
                    + "\n";
        }
        return result;
    }

    //내부 DB의 데이터를 fcm realtime database에 올리기
    public void writeNewData(String info) {
        SQLiteDatabase db = this.getReadableDatabase();
        int i=0;
        Cursor cursor = db.rawQuery("SELECT * FROM ALARMTIMES", null);
        while(cursor.moveToNext()){
            MediRef.child(info).child(String.valueOf(i++)).setValue(cursor.getString(2));
        }
    }
}