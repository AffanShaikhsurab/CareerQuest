package com.affanshaikhsurab.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.affanshaikhsurab.myapplication.R
import com.affanshaikhsurab.myapplication.activity.collage_Information
import com.affanshaikhsurab.myapplication.databinding.HorizontalListviewBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.horizontal_listview.view.*


class horizontalListView_Adapter (var name:String ,var type :String  ,var list : ArrayList<Careers> , var context : Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return list.size;
    }
    private lateinit var binding: HorizontalListviewBinding

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
                var binding:HorizontalListviewBinding = HorizontalListviewBinding.inflate(LayoutInflater.from(parent.context),parent , false)
                return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textView_HorizontalList.setText(list.get(position).name)
        if(type=="Curriculum" || type=="Scholarship")
        {
            holder.itemView.imageView_HorizontalList.setBackgroundResource(R.drawable.scholarship_bg)
        }else{
            Glide.with(context).load(list.get(position).image).into(holder.itemView.imageView_HorizontalList)

        }


        holder.itemView.imageView_HorizontalList.setOnClickListener{
            run{
                    if(type=="Collages"){
                        var intent :Intent = Intent(context,collage_Information::class.java)
                        intent.putExtra("Label" , name+"/Collages/"+list[position].name)
                        intent.putExtra("Name",list[position].name)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent)
                    }
                }
        }
    }


    inner class ViewHolder(var binding: HorizontalListviewBinding) : RecyclerView.ViewHolder(binding.root)  {
        private  var textView: TextView = binding.textViewHorizontalList
        private  var imageView: ImageView = binding.imageViewHorizontalList
        fun bind( clickListner: OnClickListner){
            binding.clickListner = clickListner
            binding.executePendingBindings()

        }

    }

    }
class OnClickListner(val clickListner:  ()-> Unit)
{
    fun onClick() = clickListner()
}




