package com.bignerdranch.android.tshirtparadise;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView shirtRecyclerView;
//    private ShirtAdapter shirtAdapter;
    private int mQuality;
    TShirtCabinet cabinet;
    TshirtAdapter teeAdapter;
    private static ArrayList<Tshirt>shoppingCart;
    private static HashMap<Integer,Tshirt> checked;
    private static final String QUALITY = "com.bignerdranch.android.tshirtparadise.muchquality";
    private static final String ORIENT = "com.bignerdranch.android.tshirtparadise.save_cart";
    private static String CART = "com.bignerdranch.android.tshirtparadise.cart";
    private static final String BUY = "com.bignerdranch.android.tshirtparadise.buy";
    private static final String CHECK = "com.bignerdranch.android.tshirtparadise.checked";
    public static final String PRODUCT_URL = "http://192.168.1.12/api/tees.php";

    public static Intent newIntent(Context context, int quality, ArrayList<Tshirt>shoppingCart, HashMap<Integer,Tshirt>hashmap){
        //creates an instance of ShopActivity class
        Intent i = new Intent(context,ShopActivity.class);
        i.putExtra(QUALITY,quality);
        i.putExtra(CART,(Serializable)shoppingCart);
        i.putExtra(CHECK,(Serializable)hashmap);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Activities are presented to the user
        Bundle bundle = getIntent().getExtras();
        if (savedInstanceState!=null){
            //if orientation is changed
            checked = (HashMap<Integer,Tshirt>)savedInstanceState.getSerializable(ORIENT);
        }
        else {
            //if you are returning from another activity
            checked = (HashMap<Integer,Tshirt>) bundle.getSerializable(CHECK);
        }
        setContentView(R.layout.activity_shop);
        cabinet = new TShirtCabinet();

        mQuality = bundle.getInt(QUALITY);
        //to transform serializable to arraylist, use getSerializable(String name)
        shoppingCart = (ArrayList<Tshirt>) bundle.getSerializable(CART);
        shirtRecyclerView = (RecyclerView)findViewById(R.id.tshirt_list);
        shirtRecyclerView.setLayoutManager(new LinearLayoutManager(ShopActivity.this));

        loadProducts();
        //update the list
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState); //what is super: Android.activity runs behind the scenes,
        //assigning a context to this activity
        savedInstanceState.putSerializable(ORIENT,checked);
    }

    private void loadProducts(){
        //gets json text from mysql server get call, then parses the jsonarray into tee shirt objects
        StringRequest stringRequest = new StringRequest(Request.Method.GET,PRODUCT_URL,
                new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    try {
                        JSONArray products=new JSONArray(response);
                        for (int i=0;i<products.length();i++){
                            JSONObject tee = products.getJSONObject(i);
                            int id = tee.getInt("id");
                            int bucket = tee.getInt("bucket");
                            String price = tee.getString("price");
                            String title = tee.getString("title");
                            String image = tee.getString("img_url");
                            Float fPrice = Float.valueOf(price);
                            Tshirt teeShirt = new Tshirt(id,fPrice,title,image);
                            cabinet.getList(bucket).add(teeShirt);
                        }
                        teeAdapter = new TshirtAdapter(cabinet.getList(mQuality),shoppingCart,checked,ShopActivity.this);
                        shirtRecyclerView.setAdapter(teeAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(ShopActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra(BUY,shoppingCart);
        data.putExtra(CHECK,checked);
        setResult(RESULT_OK,data);
        super.finish();
    }

    @Override
    protected void onDestroy(){
        //before ondestroy onActivityResult() is called in shopActivity
        super.onDestroy();
    }
}
