package com.example.movielist.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Created_by (
	val gravatar_hash : String?=null,
	val id : String?=null,
	val name : String?=null,
	val username : String?=null
) : Parcelable