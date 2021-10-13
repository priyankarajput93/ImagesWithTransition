package com.example.movielist.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build

import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.movielist.R
import com.bumptech.glide.request.target.Target
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Activity.openNewActivity(
    intent: Intent,
    bundle: Bundle? = null
) {
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun ImageView.load(
    url: String,
    loadOnlyFromCache: Boolean = false,
    onLoadingFinished: () -> Unit = {}
) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }
    val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_movie)
        .dontTransform()
        .onlyRetrieveFromCache(loadOnlyFromCache)
    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .listener(listener)
        .into(this)
}


@SuppressLint("SimpleDateFormat")
fun getFormattedDate(date: String?) : String{

    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat("dd MMM, yyyy")
    return formatter.format(parser.parse(date))
}