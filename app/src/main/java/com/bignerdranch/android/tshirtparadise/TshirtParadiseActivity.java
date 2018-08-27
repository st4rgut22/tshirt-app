package com.bignerdranch.android.tshirtparadise;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TshirtParadiseActivity extends AppCompatActivity {

    private Button mSexy0;
    private Button mSexy1;
    private Button mSexy2;
    private Button mSexy3;
    private Button mCheckOut;
    private int POOR=0;
    private HashMap<Integer,Tshirt> teeMap = new HashMap<Integer,Tshirt>();
    private ArrayList<Tshirt> shoppingCart = new ArrayList<Tshirt>();
    private static final int requestCodeShop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt_paradise);
        mSexy1 = (Button) findViewById(R.id.sexy1);
        mSexy2 = (Button) findViewById(R.id.sexy2);
        mSexy3 = (Button) findViewById(R.id.sexy3);
        mCheckOut = (Button) findViewById(R.id.checkout);

        mCheckOut.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               //take me to a new activity for purchasing in-cart items
               Intent buyIntent = BuyActivity.newIntent(TshirtParadiseActivity.this,teeMap);
               startActivity(buyIntent);
           }
        });

        mSexy1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = ShopActivity.newIntent(TshirtParadiseActivity.this,0,shoppingCart,teeMap);
                startActivityForResult(i,requestCodeShop);            }
        });

        mSexy2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = ShopActivity.newIntent(TshirtParadiseActivity.this,1,shoppingCart,teeMap);
                startActivityForResult(i,requestCodeShop);            }
        });

        mSexy3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = ShopActivity.newIntent(TshirtParadiseActivity.this,2,shoppingCart,teeMap);
                startActivityForResult(i,requestCodeShop);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==requestCodeShop) {
            shoppingCart = (ArrayList<Tshirt>)data.getExtras().getSerializable("com.bignerdranch.android.tshirtparadise.buy");
            teeMap = (HashMap<Integer,Tshirt>)data.getExtras().getSerializable("com.bignerdranch.android.tshirtparadise.checked");
            return;
        }
    }

}
