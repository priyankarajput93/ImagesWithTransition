package com.example.movielist.ui

import android.app.Activity
import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R
import com.example.movielist.api.ApiCall
import com.example.movielist.data.DataModel
import com.example.movielist.data.Results
import com.example.movielist.adapter.DataAdapter
import com.example.movielist.utils.Constants
import com.example.movielist.utils.EndlessRecyclerOnScrollListener

import retrofit2.Callback
import retrofit2.Response
import androidx.core.app.ActivityOptionsCompat

import android.os.Build

import android.content.Intent
import android.os.Parcelable
import android.transition.Explode
import android.util.Log

import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import com.example.movielist.utils.Constants.EXTRA_DATA
import com.example.movielist.utils.openNewActivity
import kotlinx.android.parcel.Parcelize


class MainActivity : AppCompatActivity(), DataAdapter.OnImageClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: DataAdapter

    private var page = 1
    private var currentCount = 0
    private var pageLimit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = GridLayoutManager(this,3)
        getAllData(page)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = manager
            myAdapter = DataAdapter(ArrayList())
            myAdapter.setOnClickListener(this@MainActivity)
            adapter = myAdapter
            scrollToPosition(currentCount)
            addOnScrollListener(object :
                EndlessRecyclerOnScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(current_page: Int) {
                    if (current_page <= pageLimit)
                        getAllData(current_page)
                    currentCount = current_page
                }
            })
        }
    }

    private fun getAllData(page: Int) {
        ApiCall.retrofitService.getAllData(1,page, Constants.API_KEY).enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: retrofit2.Call<DataModel>,
                response: Response<DataModel>
            ) {
                if (response.isSuccessful) {
                    myAdapter.setUpList(response.body()?.results as ArrayList<Results>)
                }
            }
            override fun onFailure(call: retrofit2.Call<DataModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.api_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onImageClick(view: View,data: Results) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, view.transitionName).toBundle()
        Intent(this, DetailsActivity::class.java)
            .putExtra(EXTRA_DATA, data as Parcelable)
            .let {
                startActivity(it, options)
            }
       /* val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra( EXTRA_DATA,data as Parcelable )
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view as ImageView,
            view.transitionName)
        openNewActivity(detailsIntent,options.toBundle())*/

    }
}