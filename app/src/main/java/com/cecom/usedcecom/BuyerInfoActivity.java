package com.cecom.usedcecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class BuyerInfoActivity extends AppCompatActivity {
    private final String dbName = "notifications";
    private final String tableName = "notireceived";

    SQLiteDatabase sampleDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_info);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

        }

        String seller = "";
        String item = "";
        String number = "";

        Intent intent = getIntent();
        seller = intent.getStringExtra("seller");
        item = intent.getStringExtra("item");
        number = intent.getStringExtra("number");

        TextView tvSeller = findViewById(R.id.buyer_name);
        TextView tvItem = findViewById(R.id.buyer_item);
        TextView tvNumber = findViewById(R.id.buyer_number);

        tvSeller.setText(String.format(Locale.getDefault(), "구매 희망자 닉네임 : %s", seller));
        tvItem.setText(String.format(Locale.getDefault(), "구매 희망 물품 : %s", item));
        tvNumber.setText(String.format(Locale.getDefault(), "구매 희망자 연락처 : %s", number));

        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (seller VARCHAR(20), item VARCHAR(20) );");
            sampleDB.execSQL("INSERT INTO " + tableName + " Values ('" + seller + "', '" + item + "');");
            sampleDB.close();
        } catch (SQLiteException se) {
            Log.e("SQL WRITE ERROR", se.getMessage());
        }
    }
}
