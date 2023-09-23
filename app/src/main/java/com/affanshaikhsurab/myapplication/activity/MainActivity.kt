package com.affanshaikhsurab.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import com.affanshaikhsurab.myapplication.adapter.Careers
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.affanshaikhsurab.myapplication.R
import com.affanshaikhsurab.myapplication.carrersList.careers10
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var careersArrayList = ArrayList<Careers>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creating a fragment
        val f = Fragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainLayout, careers10()).commit()
    }
}