package com.mlika.lib;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by mohamed mlika on 06/10/2018.
 * mohamedmlikaa@gmail.com
 */
public class TableEntity {
    private String tableName;
    private String structure;
    private ArrayList<LigneEntity> ligneEntities;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<LigneEntity> getLigneEntities() {
        return ligneEntities;
    }

    public void setLigneEntities(ArrayList<LigneEntity> ligneEntities) {
        this.ligneEntities = ligneEntities;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof TableEntity && ((TableEntity) obj).tableName.equals(tableName);
    }
}
