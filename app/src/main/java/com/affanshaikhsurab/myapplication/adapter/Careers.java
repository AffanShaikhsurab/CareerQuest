package com.affanshaikhsurab.myapplication.adapter;

import android.net.Uri;

public class Careers {
    private String Name ;
    private String Image ;

    public Careers(String namee , String imagee)
    {
        Image = imagee;
        Name = namee;
    }

    public Careers() {

    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }
}
