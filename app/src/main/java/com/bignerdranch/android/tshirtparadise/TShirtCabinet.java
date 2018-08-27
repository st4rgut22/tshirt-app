package com.bignerdranch.android.tshirtparadise;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class TShirtCabinet {
    private static List<List<Tshirt>> mCabinet;
    public TShirtCabinet(){
        mCabinet = new ArrayList<List<Tshirt>>();
        List<Tshirt>quality0 = new ArrayList<Tshirt>();
        List<Tshirt>quality1 = new ArrayList<Tshirt>();
        List<Tshirt>quality2 = new ArrayList<Tshirt>();
        List<Tshirt>quality3 = new ArrayList<Tshirt>();

        mCabinet.addAll(Arrays.asList(quality0,quality1,quality2,quality3));
    }

    public List<Tshirt>getList(int quality){
        return mCabinet.get(quality);
    }
}
