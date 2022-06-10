package com.capstone.rusantara.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageData(
    @field:SerializedName("img")
    val img: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("info")
    val info: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("ingredients")
    val ingredients: String,

    @field:SerializedName("nutrition")
    val nutrition: String
) : Parcelable