package com.example.moviepicker.data.model.movie.video

data class VideoResult(
    val trailers: List<VideoItem> = emptyList(),
    val teasers: List<VideoItem> = emptyList()
)
