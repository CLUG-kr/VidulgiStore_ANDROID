package com.cecom.usedcecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class BuyerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_info);

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
    }
}
