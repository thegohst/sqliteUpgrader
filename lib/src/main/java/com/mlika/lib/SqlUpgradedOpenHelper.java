package com.mlika.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Created by mohamed mlika on 05/10/2018.
 * mohamedmlikaa@gmail.com
 */
public abstract class SqlUpgradedOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = SqlUpgradedOpenHelper.class.getSimpleName();
    private String databaseName;


    public SqlUpgradedOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.databaseName = name;

    }

    public SqlUpgradedOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.databaseName = name;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SqlUpgradedOpenHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
        this.databaseName = name;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onPreUpgrade(sqLiteDatabase, i, i1);
        upgradeTables(sqLiteDatabase);
        onAfterUpgrade(sqLiteDatabase, i, i1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onPreCreate(sqLiteDatabase);
        createTables(sqLiteDatabase);
        onAfterCreate(sqLiteDatabase);
    }


    private void createTables(SQLiteDatabase sqLiteDatabase) {

        createTables(sqLiteDatabase, createOrUpgradeTables());
    }

    private void createTables(SQLiteDatabase sqLiteDatabase, ArrayList<String> structureList) {


        for (String structure : structureList) {
            try {
                sqLiteDatabase.execSQL(structure);
            } catch (Throwable ignore) {

            }
        }
    }

    private void upgradeTables(SQLiteDatabase db) {
        ArrayList<TableEntity> tableList = getTableNameAndStructure(db);


        ArrayList<String> newStructureList = createOrUpgradeTables();
        int i = 0;
        int pos;
        while (i < tableList.size()) {


            pos = contains(newStructureList, tableList.get(i).getStructure());

            if (pos >= 0) {
                tableList.remove(i);
                newStructureList.remove(pos);
            } else {

                getDataFromTable(db, tableList.get(i));
                dropTable(db, tableList.get(i).getTableName());
                i++;

            }
        }


        if (!newStructureList.isEmpty()) {

            createTables(db, newStructureList);


            ArrayList<TableEntity> newTablelist = getTableNameAndStructure(db);


            for (TableEntity tableEntity : tableList) {
                if (tableEntity.getTableName() != null && tableEntity.getLigneEntities() != null && tableEntity.getLigneEntities().size() > 0
                        && newTablelist.contains(tableEntity)) {
                    insertIntoDataBase(db, tableEntity.getTableName(), tableEntity.getLigneEntities());

                }

            }
        }
    }


    private int contains(ArrayList<String> structureList, String string) {


        if (structureList != null && string != null) {
            for (int i = 0; i < structureList.size(); i++) {

                if (structureList.get(i) != null && structureList.get(i)
                        .replaceAll("\\s+", "").toLowerCase()
                        .equals(string.replaceAll("\\s+", "").toLowerCase())) {

                    return i;
                }
            }
        }
        return -1;
    }

    private void insertIntoDataBase(SQLiteDatabase db, String tableName, ArrayList<LigneEntity> ligneList) {
        for (LigneEntity ligneEntity : ligneList) {

            insertIntoDataBase(db, tableName, ligneEntity);
        }
    }

    private void insertIntoDataBase(SQLiteDatabase db, String tableName, LigneEntity ligneEntity) {

        if (ligneEntity != null && ligneEntity.getColunmEntities() != null) {
            ContentValues contentValues = new ContentValues();

            for (ColunmEntity colunmEntity : ligneEntity.getColunmEntities()) {
                if (colunmEntity.getColunmValue() == null) {

                    continue;

                }

                if (colunmEntity.getColunmValue() instanceof Integer || colunmEntity.getColunmValue() instanceof Long) {
                    contentValues.put(colunmEntity.getColunmName(), ((long) colunmEntity.getColunmValue()));
                } else if (colunmEntity.getColunmValue() instanceof Float || colunmEntity.getColunmValue() instanceof Double) {

                    contentValues.put(colunmEntity.getColunmName(), ((double) colunmEntity.getColunmValue()));
                } else if (colunmEntity.getColunmValue() instanceof String) {
                    contentValues.put(colunmEntity.getColunmName(), (String) colunmEntity.getColunmValue());
                } else if (colunmEntity.getColunmValue() instanceof byte[]) {
                    contentValues.put(colunmEntity.getColunmName(), (byte[]) colunmEntity.getColunmValue());
                }
            }
            if (contentValues.size() > 0) {

                db.insert(tableName, null, contentValues);
            }
        }
    }


    private ArrayList<TableEntity> getTableNameAndStructure(SQLiteDatabase db) {
        ArrayList<TableEntity> tableList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT name,sql FROM sqlite_master WHERE type='table'", null);

        TableEntity tableEntity;
        if (cursor.moveToFirst()) {
            do {

                tableEntity = new TableEntity();
                tableEntity.setTableName(cursor.getString(0));
                tableEntity.setStructure(cursor.getString(1) + ";");
                if (tableEntity.getTableName() != null && !tableEntity.getTableName().equals("android_metadata")) {
                    tableList.add(tableEntity);
                }
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {

            cursor.close();
        }

        return tableList;

    }


    private void getDataFromTable(SQLiteDatabase db, TableEntity tableEntity) {


        Cursor cursor = db.query(tableEntity.getTableName(), new String[]{"*"}, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            ArrayList<LigneEntity> ligneList = new ArrayList<>();
            LigneEntity ligneEntity;

            ArrayList<ColunmEntity> colunmList;
            ColunmEntity colunmEntity;
            do {

                colunmList = new ArrayList<>();

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    colunmEntity = new ColunmEntity();
                    colunmEntity.setColunmName(cursor.getColumnName(i));
                    colunmEntity.setColunmValue(getColumnValue(cursor, i));
                    colunmList.add(colunmEntity);

                }

                ligneEntity = new LigneEntity();
                ligneEntity.setColunmEntities(colunmList);
                ligneList.add(ligneEntity);

            } while (cursor.moveToNext());
            tableEntity.setLigneEntities(ligneList);
        }

        if (!cursor.isClosed()) {

            cursor.close();
        }

    }


    private Object getColumnValue(Cursor cursor, int pos) {
        Object object = null;

        switch (cursor.getType(pos)) {
            case Cursor.FIELD_TYPE_INTEGER:
                object = cursor.getLong(pos);
                break;
            case Cursor.FIELD_TYPE_FLOAT:
                object = cursor.getDouble(pos);
                break;

            case Cursor.FIELD_TYPE_STRING:
                object = cursor.getString(pos);
                break;
            case Cursor.FIELD_TYPE_BLOB:
                object = cursor.getBlob(pos);
                break;

        }


        return object;
    }


    private void dropTable(SQLiteDatabase db, String tableName) {

        db.execSQL("DROP TABLE IF EXISTS " + tableName + ";");

    }


    public void onPreCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public void onAfterCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public void onPreUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void onAfterUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {

    }


    public abstract ArrayList<String> createOrUpgradeTables();
}
