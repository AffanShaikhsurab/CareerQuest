package com.affanshaikhsurab.myapplication.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.affanshaikhsurab.myapplication.adapter.CompanyImageView_Adapter
import com.affanshaikhsurab.myapplication.adapter.ImageView_Adapter
import com.affanshaikhsurab.myapplication.databinding.ActivityCollageInformationBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_collage_information.*

class collage_Information : AppCompatActivity() {

    private lateinit var binding: ActivityCollageInformationBinding
    var Imagelist: ArrayList<String> = ArrayList()
    var Companieslist: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var name: String = intent.getStringExtra("Label").toString()
        supportActionBar?.setTitle(intent.getStringExtra("Name").toString())
        binding = ActivityCollageInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchingData(name)
        fetchingImages(name)
        fetchingCollage(name).run { }
        fetchingReviews(name).run { }
    }

    fun fetchingData(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()

        val db: DatabaseReference = firebase.getReference(course).child("Information");
        Log.i("apiDData" , db.toString())

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot in snapshot.children) {
                    if (datasnapshot.key == "Video") {
                        binding.collageIVideoView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(datasnapshot.getValue().toString(), 0f)
                                youTubePlayer.pause()
                            }
                        })
                    }
                    if(datasnapshot.key =="Ratings")
                    {
                        Glide.with(applicationContext).load(datasnapshot.getValue()).into(binding.ratingsImageView)
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchingImages(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Information/Images");
        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    Imagelist.add(child.getValue().toString())
                    recylerView_Images.layoutManager = LinearLayoutManager(applicationContext , LinearLayoutManager.HORIZONTAL , false)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = ImageView_Adapter(Imagelist , applicationContext)
                    // adapter instance is set to the recyclerview to inflate the items.
                    recylerView_Images.adapter = itemAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    fun fetchingCollage(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Information/Companies");
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (child in snapshot.children) {

//                    var career: Careers? = child.getValue(Careers::class.java)
//                    Companieslist?.add(career!!)
                    for (compay in child.children)
                    {
                        if(compay.key =="Image")
                        {
                            Companieslist.add(compay.getValue().toString())
                        }
                        recyclerview_Companies.layoutManager = LinearLayoutManager(applicationContext , LinearLayoutManager.HORIZONTAL , false)
                        // Adapter class is initialized and list is passed in the param.
                        val itemAdapter = CompanyImageView_Adapter(Companieslist , applicationContext)
                        // adapter instance is set to the recyclerview to inflate the items.
                        recyclerview_Companies.adapter = itemAdapter
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchingReviews(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Information/Reviews");
        db.addValueEventListener(object : ValueEventListener {
            @SuppressLint("ResourceType")
            override fun onDataChange(snapshot: DataSnapshot) {

                for (child in snapshot.children) {

                        if(child.key =="Events")
                        {

                            val params: ViewGroup.LayoutParams = eventsProgressBar.layoutParams
                            params.width =  (cardview.width * child.getValue().toString().toInt()) / 100
                            eventsProgressBar.layoutParams = params
                        }
                    if(child.key =="Facilities")
                    {
                        val params: ViewGroup.LayoutParams = facilitiesProgressBar.layoutParams
                        params.width =  (cardview.width * child.getValue().toString().toInt()) / 100
                        facilitiesProgressBar.layoutParams = params
                    }
                    if(child.key =="Overall")
                    {
                        val params: ViewGroup.LayoutParams = overallProgressBar.layoutParams
                        params.width =  (cardview.width * child.getValue().toString().toInt()) / 100
                        overallProgressBar.layoutParams = params
                    }
                    if(child.key =="Teaching")
                    {

                        val params: ViewGroup.LayoutParams = teachingProgresBar.layoutParams
                        params.width =  (cardview.width * child.getValue().toString().toInt()) / 100
                        teachingProgresBar.layoutParams = params
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}