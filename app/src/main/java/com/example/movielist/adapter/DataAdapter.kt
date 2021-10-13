package com.example.movielist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movielist.R
import com.example.movielist.data.Results
import com.example.movielist.utils.Constants.IMAGE_PATH
import com.example.movielist.utils.load


class DataAdapter(private val data: ArrayList<Results>) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    lateinit var listener: OnImageClickListener

    fun setOnClickListener(listener: OnImageClickListener){
        this.listener = listener
    }

    class MyViewHolder(private val view: View,var listener: OnImageClickListener) : RecyclerView.ViewHolder(view) {

        fun bind(data: Results) {
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val imagePath = IMAGE_PATH.plus(data.poster_path)
            imageView.apply {
               load(imagePath)
                transitionName = imagePath
                setOnClickListener { listener.onImageClick(it,data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(v,listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpList(list: ArrayList<Results>) {
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    interface OnImageClickListener {
        fun onImageClick(view: View,data: Results)
    }
}