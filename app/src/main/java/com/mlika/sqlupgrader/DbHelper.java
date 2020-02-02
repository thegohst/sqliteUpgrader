package com.mlika.sqlupgrader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mlika.lib.SqlUpgradedOpenHelper;

import java.util.ArrayList;

/**
 * Created by mohamed mlika on 29/06/2018.
 * mohamedmlikaa@gmail.com
 */
public class DbHelper extends SqlUpgradedOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    public static int DB_VERSION = 18;
    public static final String DB_NAME = "mlm.db";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static final String TABLE_ITEM_ENTITY = "item_entity";

    public static final String id = "id";
    public static final String name = "name";
    public static final String cleanPrice = "clean_price";
    public static final String dirtyPrice = "dirty_price";
    public static final String url = "url";
    public static final String pictureURl = "picture_url";
    public static final String timeStamp = "timeStamp";
    public static final String isRequestFailed = "is_request_failed";

    private static final String CREATE_TABLE_ITEM_ENTITY = "CREATE TABLE " + TABLE_ITEM_ENTITY + " (" +
            id + " INTEGER , " +
            name + " TEXT  ," +
            url + " TEXT);";


    private static final String CREATE_TABLE_ITEM_ENTITY2 = "CREATE TABLE  AZERT (" +
            id + " INTEGER , " +
            name + " TEXT  ," +
            url + " TEXT);";

    @Override
    public ArrayList<String> createOrUpgradeTables() {

        ArrayList<String> list = new ArrayList<>();

        list.add(CREATE_TABLE_ITEM_ENTITY);
        list.add(CREATE_TABLE_ITEM_ENTITY2);
        return list;
    }



}
