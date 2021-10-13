package com.example.movielist.ui


import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.transition.*
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.transition.doOnEnd

import com.example.movielist.R
import com.example.movielist.data.Results
import com.example.movielist.utils.Constants.EXTRA_DATA
import com.example.movielist.utils.Constants.IMAGE_PATH
import com.example.movielist.utils.getFormattedDate
import com.example.movielist.utils.load
import java.time.format.DateTimeFormatter
import java.util.*


class DetailsActivity : AppCompatActivity() {

    private val data : Results by lazy  {
        intent.getParcelableExtra(EXTRA_DATA)!!
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        val imageView = findViewById<ImageView>(R.id.image)
        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        val txtDuration = findViewById<TextView>(R.id.txtDuration)
        val txtMovieType= findViewById<TextView>(R.id.txtMovieType)
        val txtMovieThriller = findViewById<TextView>(R.id.txtMovieThriller)
        val txtDrama = findViewById<TextView>(R.id.txtDrama)
        val btnBookNow = findViewById<TextView>(R.id.btnBookNow)
        val txtReview = findViewById<TextView>(R.id.txtReviews)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val txtRating = findViewById<TextView>(R.id.txtRating)

        val dataImage = IMAGE_PATH.plus(data.poster_path)
        supportPostponeEnterTransition()
        imageView.transitionName = dataImage
        imageView.load(dataImage, true) {
            supportStartPostponedEnterTransition()
        }
        window.sharedElementEnterTransition = TransitionSet()
            .addTransition(ChangeImageTransform())
            .addTransition(ChangeBounds())
            .apply {
                doOnEnd { imageView.load(dataImage) }
            }
        window.enterTransition = Fade().apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
            excludeTarget(R.id.action_bar_container, true)
        }
        txtTitle.text = data.title
        txtDescription.text = data.overview
        txtDuration.text = getFormattedDate(data.release_date)

        txtReview.text = getString(R.string.reviews_count, data.vote_count)
        txtRating.text = data.vote_average?.toString()
        ratingBar.rating = data.vote_average?.toFloat()!!
       /* txtMovieType.text = data.overview
        txtMovieThriller.text = data.overview
        txtDrama.text = data.overview*/
        btnBookNow.setOnClickListener {
            Toast.makeText(this,getString(R.string.coming_soon),Toast.LENGTH_SHORT).show()
        }

    }
}