package com.example.flixter

class BestMovie(Title:String, Description:String, PosterLink:String) {

    val title = Title
    val description = Description
    val full_link = "https://image.tmdb.org/t/p/w500/" + PosterLink
}