package com.affanshaikhsurab.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.affanshaikhsurab.myapplication.R
import com.affanshaikhsurab.myapplication.adapter.Careers
import com.affanshaikhsurab.myapplication.adapter.OnClickListner
import com.affanshaikhsurab.myapplication.adapter.horizontalListView_Adapter
import com.affanshaikhsurab.myapplication.databinding.CourseInformationBinding
import com.google.firebase.database.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_courses_list.*
import kotlinx.android.synthetic.main.course_information.*
import kotlinx.android.synthetic.main.list.*

/**
 * A simple [Fragment] subclass.
 * Use the [coursesInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class coursesInformation : AppCompatActivity()   {
    // TODO: Rename and change types of parameters
    var Curriculumlist: ArrayList<Careers> = ArrayList()
    var list: ArrayList<Careers> = ArrayList()
    var Collagelist: ArrayList<Careers> = ArrayList()
    var Scolarshiplist: ArrayList<Careers> = ArrayList()
    private lateinit var binding: CourseInformationBinding;
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.course_information)
        var mParam1: String? = null
        var mParam2: String? = null
        setTitle("Courses Info")
        name = intent.getStringExtra("Label").toString()
        fetchingData(name).run { }
        fetchingCurriculum(name).run { }
        fetchingCollage(name).run { }
        fetchingScolarship(name).run { }
    }


    fun fetchingData(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Information");
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot in snapshot.children) {
                    if (datasnapshot.key == "Jobs") {
                        binding.totalNoOfJobsTextView.setText(datasnapshot.getValue().toString())
                    }
                    if (datasnapshot.key == "Package") {
                        binding.salaryTextView.setText(datasnapshot.getValue().toString())
                    }
                    if (datasnapshot.key == "Years") {
                        binding.totalNoOfYearsTextview.setText(datasnapshot.getValue().toString())
                    }
                    if (datasnapshot.key == "Video") {
                        binding.collageVideoView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(datasnapshot.getValue().toString(), 0f)
                                youTubePlayer.pause()
                            }
                        })
                    }
                    if (datasnapshot.key == "WDTD"){
                        binding.WTDTCoursesInformation.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(datasnapshot.getValue().toString(), 0f)
                                youTubePlayer.pause()
                            }
                        })
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchingCurriculum(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Curriculum");
        db.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    var career: Careers? = child.getValue(Careers::class.java)
                    Curriculumlist?.add(career!!)
                    recylerView_collageImages.layoutManager = LinearLayoutManager(applicationContext , LinearLayoutManager.HORIZONTAL , false)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = horizontalListView_Adapter( name ,"Curriculum", Curriculumlist , applicationContext )
                    // adapter instance is set to the recyclerview to inflate the items.
                    recylerView_collageImages.adapter = itemAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    fun fetchingCollage(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Colleges");
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (child in snapshot.children) {
                    var career: Careers? = child.getValue(Careers::class.java)
                    Collagelist?.add(career!!)
                    recylerView_collageLists.layoutManager = LinearLayoutManager(applicationContext , LinearLayoutManager.HORIZONTAL , false)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = horizontalListView_Adapter(name,"Colleges",Collagelist , applicationContext )
                    // adapter instance is set to the recyclerview to inflate the items.
                    recylerView_collageLists.adapter = itemAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchingScolarship(course: String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference(course).child("Scholarship");
        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (child in snapshot.children) {
                    var career: Careers? = child.getValue(Careers::class.java)
                    Scolarshiplist?.add(career!!)
                    recylerView_scholarshipList.layoutManager = LinearLayoutManager(applicationContext , LinearLayoutManager.HORIZONTAL , false)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = horizontalListView_Adapter(name,"Scholarship" ,Scolarshiplist , applicationContext )
                    // adapter instance is set to the recyclerview to inflate the items.
                    recylerView_scholarshipList.adapter = itemAdapter
                    }
                }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



}
