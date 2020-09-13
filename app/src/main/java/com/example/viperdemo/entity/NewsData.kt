package com.example.viperdemo.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsData(val articles: List<Article>) : Parcelable

@Parcelize
data class Article(
    val source: Source,
    val title: String,
    val url: String,
    @Json(name = "urlToImage") val image: String) : Parcelable

@Parcelize
data class Source(val name: String) : Parcelable