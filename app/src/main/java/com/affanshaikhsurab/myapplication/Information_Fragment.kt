package com.affanshaikhsurab.myapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.affanshaikhsurab.myapplication.activity.coursesInformation
import com.affanshaikhsurab.myapplication.adapter.ImageView_Adapter
import com.affanshaikhsurab.myapplication.carrersList.coursesList
import com.affanshaikhsurab.myapplication.databinding.FragmentInformationBinding
import com.google.firebase.database.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_collage_information.*
import kotlinx.android.synthetic.main.activity_courses_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [Information_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Information_Fragment : Fragment() {
    private lateinit var binding: FragmentInformationBinding;

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_information_, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val i = requireActivity().intent
        requireActivity().title = i.getStringExtra("Label")
        val name = i.getStringExtra("Label").toString().trim()
        var dialog: Dialog? = context?.let { Dialog(it, R.drawable.image_shape) }

        if (dialog != null) {
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

    fetchingImages(name)
    //        val b = binding!!.co
//        b.setOnClickListener {
//            val intent = Intent(context, coursesInformation::class.java)
//            intent.putExtra("Career", name)
//            startActivity(intent)
//        }
    }
    fun fetchingImages( name :String) {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val db: DatabaseReference = firebase.getReference("Careers").child(name).child("Information");
        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                   if(child.key == "WDTD"){
                       binding.WDTDYoutubePlayerInformation.addYouTubePlayerListener(object:AbstractYouTubePlayerListener(){
                           override fun onReady(youTubePlayer: YouTubePlayer) {
                               super.onReady(youTubePlayer)
                               youTubePlayer.loadVideo(child.value.toString() , 0f)
                               youTubePlayer.pause()
                           }
                       })
                }
                    if(child.key == "WIS"){
                        binding.WISYoutubePlayerInformation.addYouTubePlayerListener(object:AbstractYouTubePlayerListener(){
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(child.value.toString() , 0f)
                                youTubePlayer.pause()
                            }
                        })
                    }
            }
        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }) }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Information_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): Information_Fragment {
            val fragment = Information_Fragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}



