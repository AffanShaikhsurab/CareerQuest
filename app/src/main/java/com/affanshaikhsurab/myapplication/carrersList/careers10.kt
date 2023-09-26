package com.affanshaikhsurab.myapplication.carrersList

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.affanshaikhsurab.myapplication.R
import com.affanshaikhsurab.myapplication.activity.ChatBot
import com.affanshaikhsurab.myapplication.activity.Information
import com.affanshaikhsurab.myapplication.activity.recommendation
import com.affanshaikhsurab.myapplication.adapter.CareerAdapter
import com.affanshaikhsurab.myapplication.adapter.Careers
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Array

/**
 * A simple [Fragment] subclass.
 * Use the [careers10.newInstance] factory method to
 * create an instance of this fragment.
 */
class careers10 (val recommendation: ArrayList<String>): Fragment( ) {
    var careersArrayList = ArrayList<Careers>()
    var recommended = ArrayList<Careers>()

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen
        return inflater.inflate(R.layout.fragment_careers10, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = view.findViewById<ListView>(R.id.carrers_10_list)
        val list1= view.findViewById<ListView>(R.id.carrers_10_list_recommended)

            val recommedList: ArrayList<String>? = recommendation
            Log.i("apiData", recommendation.toString())
            val fetchingData: FetchingData = FetchingData(view.context, view, recommedList!!)
           lifecycleScope.launch {
               withContext(Dispatchers.IO){
                   fetchingData.doInBackground()

               }
           }
        val chatbotButton :FloatingActionButton =  view.findViewById(R.id.chatbotButton)
        chatbotButton.setOnClickListener {
            startActivity(Intent(view.context , ChatBot::class.java))
        }
        list.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, Information::class.java)
            intent.putExtra("Label", careersArrayList[position].name)
            startActivity(intent)
        }

        list1.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, Information::class.java)
            intent.putExtra("Label", recommended[position].name)
            startActivity(intent)
        }

    }

 inner   class FetchingData(var context: Context, var view: View , var recomList : ArrayList<String>)
        {
        lateinit var listView: ListView


        suspend fun doInBackground(vararg p0: Void?) = withContext(Dispatchers.Default){
            val fd = FirebaseDatabase.getInstance()
            val dr = fd.getReference("Careers")
            dr.addValueEventListener(object : ValueEventListener {
                val TAG = "a"
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        careersArrayList.add(
                            Careers(
                                snapshot.child("Name").getValue(
                                    String::class.java
                                ), snapshot.child("Image").getValue(
                                    String::class.java
                                )
                            )
                        )
                    }
                    Log.i("apiData", "recomList " + recomList)

                    for (career in careersArrayList) {
                        if (recomList.contains (career.name)){
                            Log.i("apiData", "career " + career.name)

                            recommended.add(career)
                        }
                    }
                    for(career in recommended){
                        careersArrayList.remove(career)
                    }

                    val careerAdapter1 = CareerAdapter(context, R.layout.list, recommended)
                    listView = view.findViewById(R.id.carrers_10_list_recommended)
                    listView.setAdapter(careerAdapter1)


                    val careerAdapter = CareerAdapter(context, R.layout.list, careersArrayList)
                    listView = view.findViewById(R.id.carrers_10_list)
                    listView.setAdapter(careerAdapter)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            }
            )
    }


}}