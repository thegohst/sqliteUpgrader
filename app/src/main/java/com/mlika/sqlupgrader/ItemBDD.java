package com.mlika.sqlupgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mohamed mlika on 30/06/2018.
 * mohamedmlikaa@gmail.com
 */
public class ItemBDD {

    private static final int VERSION_BDD = DbHelper.DB_VERSION;
    private static final String NAME_BDD = DbHelper.DB_NAME;
    private SQLiteDatabase bdd;
    private DbHelper dbHelper;
    private String table = DbHelper.TABLE_ITEM_ENTITY;
    private Context context;

    public ItemBDD(Context context) {
        super();
        dbHelper = new DbHelper(context, NAME_BDD, null, DbHelper.DB_VERSION);

        this.context = context;
    }

    public void open() {
        bdd = dbHelper.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }


    public ArrayList<ItemEntity> selectAll() {
        ArrayList<ItemEntity> list = new ArrayList<>();


        Cursor cursor = bdd.query(table, new String[]{"*"}, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {


            ItemEntity entity;
            do {

                Log.w("dddjjd", "mlika id value:" + cursor.getInt(0));

            } while (cursor.moveToNext());


        }

        if (!cursor.isClosed()) {

            cursor.close();
        }

        return list;
    }


    public void insert() {
        ContentValues values = new ContentValues();
        values.put(DbHelper.id, 1323);
        values.put(DbHelper.name,"dddddsd");
        values.put(DbHelper.url,"dnadnasldnasldnlasnda");
        bdd.insert(table, null, values);

    }

    public void remove(String url) {

        bdd.delete(table, DbHelper.url + " = '" + url + "'", null);
    }

    public void removeById(int id) {

        bdd.delete(table, DbHelper.id + " = " + id, null);
    }


    private ContentValues getContentValues(ItemEntity entity) {

        ContentValues values = new ContentValues();
        values.put(DbHelper.id, entity.getId());
        values.put(DbHelper.name, entity.getName());
        values.put(DbHelper.cleanPrice, entity.getCleanPrice());
        values.put(DbHelper.dirtyPrice, entity.getDirtyPrice());
        values.put(DbHelper.url, entity.getUrl());
        values.put(DbHelper.pictureURl, entity.getPictureUrl());
        values.put(DbHelper.timeStamp, entity.getTimeStamp());
        values.put(DbHelper.isRequestFailed, entity.isPreviousRequestFailed() ? 1 : 0);
        return values;
    }


    public void updateIsRequestFailed(boolean isRequestFailed, String url) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.isRequestFailed, isRequestFailed);
        bdd.update(table, contentValues, DbHelper.url
                + " = '" + url + "'", null);
    }

    private ItemEntity getDataFromCursor(Cursor cursor) {

        ItemEntity entity = new ItemEntity();
        entity.setId(cursor.getInt(0));
        entity.setName(cursor.getString(1));
        entity.setCleanPrice(cursor.getFloat(2));
        entity.setDirtyPrice(cursor.getString(3));
        entity.setUrl(cursor.getString(4));
        entity.setPictureUrl(cursor.getString(5));
        entity.setTimeStamp(cursor.getLong(6));

        if (entity.getPictureUrl() != null) {
            entity.setPictureUrl(entity.getPictureUrl()
                    .replaceAll("AC_SY200", "SL1500"));
        }

        return entity;

    }
}
