package com.karumi.dribbble.model

typealias Photos = List<Photo>

data class Photo(
    val photoUrl: String,
    val title: String,
    val author: String
)