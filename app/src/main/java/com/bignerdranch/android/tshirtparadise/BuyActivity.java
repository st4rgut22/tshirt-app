package com.bignerdranch.android.tshirtparadise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Inflater;

public class BuyActivity extends AppCompatActivity {
    private static final String buyShirts = "com.bignerdranch.android.tshirtparadise.buyShirts";

    public static Intent newIntent(Context context, HashMap<Integer,Tshirt>hashMap){
        Intent i = new Intent(context,BuyActivity.class);
        i.putExtra(buyShirts,hashMap);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        TextView orderSummary = (TextView) findViewById(R.id.orderSummary);
        Bundle bundle = getIntent().getExtras();
        HashMap<Integer,Tshirt> hashMap = (HashMap<Integer,Tshirt>)bundle.getSerializable(buyShirts);
        Float total=0f;
        Float price;
        String title;
        int quantity;
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            Tshirt tee = (Tshirt) pair.getValue();
            title = tee.getTitle();
            price = tee.getPrice() * tee.getQuantity();
            quantity = tee.getQuantity();
            orderSummary.append( quantity + " " + title + "\t\t" + price + "\n");
            total+=price;
        }
        orderSummary.append("Total: $" + total);
    }
}
