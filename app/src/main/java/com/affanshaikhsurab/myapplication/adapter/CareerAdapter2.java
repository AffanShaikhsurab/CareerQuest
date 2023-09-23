package com.affanshaikhsurab.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.affanshaikhsurab.myapplication.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CareerAdapter2 extends ArrayAdapter<Careers> {




    public CareerAdapter2(Context applicationContext, int list, ArrayList<Careers> carrer) {
        super(applicationContext , 0 , carrer);
    }

    @Override
    public void add(@Nullable Careers object) {
        super.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listview = convertView;
        if (listview == null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.list , parent , false);

        }
       Careers career = getItem(position);

        TextView Name = listview.findViewById(R.id.textView);
        Name.setText(career.getName());

        ImageView image = listview.findViewById(R.id.imageView);
        Glide.with(getContext()).load(career.getImage()).into(image);

        return listview;
    }
}
