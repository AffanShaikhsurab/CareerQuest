package com.affanshaikhsurab.myapplication.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class recommendation : ViewModel(){
    var recommendatio : MutableLiveData<ArrayList<String>> = MutableLiveData()

    fun getRecommendation() :MutableLiveData<ArrayList<String>>{
        return recommendatio
    }

}