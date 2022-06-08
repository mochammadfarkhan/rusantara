package com.capstone.rusantara.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageData(
    val img: String,
    val name: String,
    val info: String,
    val ingredients: String,
    val nutrition: String
) : Parcelable