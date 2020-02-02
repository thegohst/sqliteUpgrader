package com.mlika.sqlupgrader;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ItemBDD itemBDD = new ItemBDD(getApplicationContext());
        itemBDD.open();
        itemBDD.selectAll();
        itemBDD.close();
    }
}
