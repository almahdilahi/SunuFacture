package com.limamoulayeka.sunufacture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class Databasehelper extends SQLiteOpenHelper {

    private static final  String TAG = "DatabaseHelper";

    private static final  String DATABASE_NAME = "sunufacture.db";

    private static final  String TABLE_NAME = "user";
    private static final  String COL1 = "IDu";
    private static final  String COL2 = "username";
    private static final  String COL3 = "login";
    private static final  String COL4 = "password";
    private static final  String COL5 = "role";

    private static final  String TABLE_RULE = "rule";
    private static final  String COLRULE1 = "IDr";
    private static final  String COLRULE2 = "rulename";

    private static final  String TABLE_BILL = "bill";
    private static final  String COLBILL1 = "IDb";
    private static final  String COLBILL2 = "amount";
    private static final  String COLBILL3 = "date";
    private static final  String COLBILL4 = "state";
    private static final  String COLBILL5 = "user";

    private static final  String TABLE_SETTLE = "settlement";
    private static final  String COLSETTLE1 = "IDs";
    private static final  String COLSETTLE2 = "amount";
    private static final  String COLSETTLE3 = "date";
    private static final  String COLSETTLE4 = "bill";



    public Databasehelper(Context context){
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (IDu INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT UNIQUE, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " INTEGER)";

        String createTableRule = "CREATE TABLE " + TABLE_RULE + " (IDr INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLRULE2 + " TEXT UNIQUE)";

        String createTableBill = "CREATE TABLE " + TABLE_BILL + " (IDb INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLBILL2 + " INTEGER, " + COLBILL3 + " TEXT, " + COLBILL4 + " INTEGER, " + COLBILL5 + " INTEGER)";

        String createTableSettle = "CREATE TABLE " + TABLE_SETTLE + " (IDs INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLSETTLE2 + " INTEGER, " + COLSETTLE3 + " TEXT, " + COLSETTLE4 + " INTEGER)";

        db.execSQL(createTable);
        db.execSQL(createTableRule);
        db.execSQL(createTableBill);
        db.execSQL(createTableSettle);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
    db.execSQL("DROP IF TABLE EXISTS " + TABLE_RULE);
    db.execSQL("DROP IF TABLE EXISTS " + TABLE_BILL);
    db.execSQL("DROP IF TABLE EXISTS " + TABLE_SETTLE);

    onCreate(db);
    }

    public  boolean addData(String item1, String item2, String item3, int item4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);
        contentValues.put(COL5, item4);

        Log.d(TAG, "addData: Adding " + item1 + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + item2 + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + item3 + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + item4 + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public  boolean addDataRule(String item1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLRULE2, item1);

        Log.d(TAG, "addDataRule: Adding " + item1 + " to " + TABLE_RULE);

        long result = db.insert(TABLE_RULE,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public  boolean addDataBill(int item1, String item2, int item3, int item4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLBILL2, item1);
        contentValues.put(COLBILL3,item2);
        contentValues.put(COLBILL4, item3);
        contentValues.put(COLBILL5, item4);

        Log.d(TAG, "addData: Adding " + item1 + " to " + TABLE_BILL);
        Log.d(TAG, "addData: Adding " + item2 + " to " + TABLE_BILL);
        Log.d(TAG, "addData: Adding " + item3 + " to " + TABLE_BILL);
        Log.d(TAG, "addData: Adding " + item4 + " to " + TABLE_BILL);

        long result = db.insert(TABLE_BILL,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public  void updateStateBill(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_BILL + " SET " + COLBILL4 + " = '" + 1 + "' WHERE " + COLBILL1 + " = '" + id + "'";
        Log.d(TAG, "addData: Adding " + id + " to " + TABLE_BILL);
        db.execSQL(query);
    }

    public  boolean addDataSettle(int item1, String item2, int item3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLSETTLE2, item1);
        contentValues.put(COLSETTLE3,item2);
        contentValues.put(COLSETTLE4, item3);

        Log.d(TAG, "addData: Adding " + item1 + " to " + TABLE_SETTLE);
        Log.d(TAG, "addData: Adding " + item2 + " to " + TABLE_SETTLE);
        Log.d(TAG, "addData: Adding " + item3 + " to " + TABLE_SETTLE);

        long result = db.insert(TABLE_SETTLE,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
       SQLiteDatabase db = this.getWritableDatabase();
       String query = "SELECT * FROM " + TABLE_NAME;
       Cursor data = db.rawQuery(query, null);
       return data;
    }

    public Cursor getDataRule(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RULE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataBill(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataBillUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL + " WHERE " + COLBILL5 + " = '" + id + "'" ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataBillPaidUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL + " WHERE " + COLBILL5 + " = '" + id + "'" + " AND " + COLBILL4 + " = '" + 1 + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataBillUnpaidUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL + " WHERE " + COLBILL5 + " = '" + id + "'" + " AND " + COLBILL4 + " = '" + 0 + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataBillUnpaid(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL + " WHERE " + COLBILL4 + " = '" + 0 + "'" ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataBillPaid(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL + " WHERE " + COLBILL4 + " = '" +  1 + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataSettle(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SETTLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String searchPass(String log){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT login, password FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String a,b;
        b = "not found";
        if(data.moveToFirst()){
            do{
                a = data.getString(0);
                if(a.equals(log)){
                    b = data.getString(1);
                }

            }while (data.moveToNext());

        }
        return b;
    }

    public int getRuleUser(String log){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT login, password, role FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String a;
        int b;
        b = 0;
        if(data.moveToFirst()){
            do{
                a = data.getString(0);
                if(a.equals(log)){
                    b = data.getInt(2);
                }

            }while (data.moveToNext());

        }
        return b;
    }

    public int getIdRule(String log){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RULE;
        Cursor data = db.rawQuery(query, null);
        String a;
        int b;
        b = 0;
        if(data.moveToFirst()){
            do{
                a = data.getString(1);
                if(a.equals(log)){
                    b = data.getInt(0);
                }

            }while (data.moveToNext());

        }
        return b;
    }

    public String getRuleById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RULE;
        Cursor data = db.rawQuery(query, null);
        String b;
        b="not found";
        int a;
        if(data.moveToFirst()){
            do{
                a = data.getInt(0);
                if(a == id){
                    b = data.getString(1);
                }

            }while (data.moveToNext());

        }
        return b;
    }


    public int getIdUser(String log){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String a;
        int b;
        b = 0;
        if(data.moveToFirst()){
            do{
                a = data.getString(1);
                if(a.equals(log)){
                    b = data.getInt(0);
                }

            }while (data.moveToNext());

        }
        return b;
    }


    public int getIdUserByLogin(String log){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String a;
        int b;
        b = 0;
        if(data.moveToFirst()){
            do{
                a = data.getString(2);
                if(a.equals(log)){
                    b = data.getInt(0);
                }

            }while (data.moveToNext());

        }
        return b;
    }

    public String getUserById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String b;
        b="not found";
        int a;
        if(data.moveToFirst()){
            do{
                a = data.getInt(0);
                if(a == id){
                    b = data.getString(1);
                }

            }while (data.moveToNext());

        }
        return b;
    }


    public int getBillAmountById(int log){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BILL;
        Cursor data = db.rawQuery(query, null);
        int a,b;
        b = 0;
        if(data.moveToFirst()){
            do{
                a = data.getInt(0);
                if(a == log){
                    b = data.getInt(1);
                }

            }while (data.moveToNext());

        }
        return b;
    }



    public String userConnect(String log,String pas){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT username, login, password FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String a,b,c;
        c = "not found";
        if(data.moveToFirst()){
            do{
                a = data.getString(1);
                b = data.getString(2);

                if(a.equals(log) && b.equals(pas)){
                    c = data.getString(0);
                }

            }while (data.moveToNext());

        }
        return c;
    }



}
