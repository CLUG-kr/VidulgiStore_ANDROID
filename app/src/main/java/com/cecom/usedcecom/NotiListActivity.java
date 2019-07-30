package com.cecom.usedcecom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class NotiListActivity extends AppCompatActivity {
    private final String dbName = "notifications";
    private final String tableName = "notireceived";
    private static final String TAG_SELLER = "seller";
    private static final String TAG_ITEM ="item";

    ListAdapter adapter;
    ArrayList<HashMap<String, String>> personList = new ArrayList<>();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_list);

        list = findViewById(R.id.listView);
        showList();
    }

    protected void showList(){

        try {
            HashMap<String,String> header = new HashMap<>();
            header.put(TAG_SELLER,"구매 희망자");
            header.put(TAG_ITEM,"구매 희망 물품");
            personList.add(header);

            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String sellerStr = c.getString(c.getColumnIndex("seller"));
                        String itemStr = c.getString(c.getColumnIndex("item"));

                        HashMap<String,String> persons = new HashMap<>();
                        persons.put(TAG_SELLER,sellerStr);
                        persons.put(TAG_ITEM,itemStr);
                        personList.add(persons);
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

            adapter = new SimpleAdapter(
                    this, personList, R.layout.list_item,
                    new String[]{TAG_SELLER,TAG_ITEM},
                    new int[]{ R.id.seller, R.id.item}
            );
            list.setAdapter(adapter);
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  "수신된 알림이 없습니다.", Toast.LENGTH_LONG).show();
        }

    }
}
