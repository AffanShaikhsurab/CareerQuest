package com.affanshaikhsurab.myapplication.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.affanshaikhsurab.myapplication.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.company_horizontal_listview.view.*
import kotlinx.android.synthetic.main.horizontal_listview.view.*
import kotlinx.android.synthetic.main.imageview_list.view.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageView_Adapter (var list : ArrayList<String>, var context : Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
         var view : View = LayoutInflater.from(context).inflate(R.layout.imageview_list , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(context).load(list.get(position)).into(holder.itemView.imageView_ImageviewList)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
           val  imageview = itemView.findViewById<ImageView>(R.id.imageView_ImageviewList)
        }
    }

}

