package com.bignerdranch.android.tshirtparadise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TshirtAdapter extends RecyclerView.Adapter<TshirtAdapter.ShirtHolder>{
    private String TAG = "TshirtAdapter";
    private List<Tshirt> teeList;
    private HashMap<Integer,Tshirt> teeMap;
    private Context mContext;
    private static ArrayList<Tshirt> shoppingCart;

    public TshirtAdapter(List<Tshirt> teeShirts, ArrayList<Tshirt>cart, HashMap<Integer,Tshirt> checkedTees, Context ctx){
        teeMap = checkedTees;
        teeList = teeShirts;
        shoppingCart = cart;
        mContext = ctx;
    }

    @Override
    public TshirtAdapter.ShirtHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.list_item_tshirt,parent,false);
        return new ShirtHolder(viewHolder);
    }
    public class ShirtHolder extends RecyclerView.ViewHolder{
        //render the view, don't add data yet
        private TextView title;
        private CheckBox bought;
        private ImageView preview;
        private TextView price;
        private ImageButton plusBtn;
        private ImageButton minusBtn;
        private TextView quantity;
        public ShirtHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.tshirt_title);
            bought = itemView.findViewById(R.id.tshirt_checkbox);
            preview = itemView.findViewById(R.id.tshirt_picture);
            price = itemView.findViewById(R.id.tshirt_price);
            plusBtn = itemView.findViewById(R.id.plus_btn);
            minusBtn = itemView.findViewById(R.id.minus_btn);
            quantity = itemView.findViewById(R.id.number_picker);
        }

        public void bindShirt(final Tshirt tee){
            title.setText(tee.getTitle());
            price.setText(Float.toString(tee.getPrice()));
            Glide.with(mContext)
                    .load(tee.getPreviewImg())
                    .into(this.preview);
            if (teeMap.containsKey(tee.getId())){
                bought.setChecked(true);
                int q = teeMap.get(tee.getId()).getQuantity(); //quantity
                tee.setQuantity(q);
                quantity.setText(Integer.toString(q));
            }

            plusBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    tee.addTee();
                    teeMap.put(tee.getId(),tee);
                    quantity.setText(Integer.toString(tee.getQuantity()));
                }
            });

            minusBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (tee.getQuantity()>0) {
                        tee.removeTee();
                        teeMap.put(tee.getId(),tee);
                        quantity.setText(Integer.toString(tee.getQuantity()));
                    }
                }
            });

            bought.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton btn,boolean isChecked){
                    if (btn.isChecked()){
                        //adds to hashmap
                        teeMap.put(tee.getId(),tee);
                        Toast added = Toast.makeText(mContext,"tee shirt added", Toast.LENGTH_SHORT);
                        added.show();
                    }
                    else { //unchecked remove the t shirt, but refs are different
                        teeMap.remove(tee.getId());
                        Toast removed = Toast.makeText(mContext,"tee shirt removed",Toast.LENGTH_SHORT);
                        removed.show();
                    }
                }
            });

            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = BigImgActivity.newIntent(mContext,tee.getPreviewImg());
                    mContext.startActivity(i);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ShirtHolder shirtHolder, int position){
        Tshirt t = teeList.get(position);
        shirtHolder.bindShirt(t);
    }

    @Override
    public int getItemCount(){
        return teeList.size();
    }

    public ArrayList<Tshirt>getShoppingCart(){
        return shoppingCart;
    }

}
