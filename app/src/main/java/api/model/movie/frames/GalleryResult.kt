package api.model.movie.frames

import com.truedev.kinoposk.api.model.movie.frames.GalleryItem

data class GalleryResult(
    val frames: List<GalleryItem> = emptyList()
)
