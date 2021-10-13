package com.example.movielist.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel (

	val average_rating : Double?=null,
	val backdrop_path : String?=null,
	val created_by : Created_by?=null,
	val description : String?=null,
	val id : Int?=null,
	val iso_3166_1 : String?=null,
	val iso_639_1 : String?=null,
	val name : String?=null,
	val page : Int?=null,
	val poster_path : String?=null,
	val public : Boolean?=null,
	val results : List<Results>?=null,
	val sort_by : String?=null,
	val total_pages : Int?=null,
	val total_results : Int?=null
) : Parcelable