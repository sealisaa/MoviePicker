package com.example.moviepicker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import data.model.Movie
import com.example.moviepicker.R
import com.example.moviepicker.databinding.HorizontalMovieCardBinding

class HorizontalMovieCardAdapter : RecyclerView.Adapter<HorizontalMovieCardAdapter.HorizontalCardMovieHolder>() {
    val movieCardList = ArrayList<Movie>()

    class HorizontalCardMovieHolder(movieCard: View) : RecyclerView.ViewHolder(movieCard) {
        val binding = HorizontalMovieCardBinding.bind(movieCard)

        fun bind(movie: Movie) = with(binding) {
            movieTitle.text = movie.title
            directorName.text = movie.directorName
            moviePoster.setImageResource(movie.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalCardMovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_movie_card, parent, false)
        return HorizontalCardMovieHolder(view)
    }

    override fun getItemCount(): Int {
        return movieCardList.size
    }

    override fun onBindViewHolder(holder: HorizontalCardMovieHolder, position: Int) {
        holder.bind(movieCardList[position])
    }
    fun addItem(movie: Movie) {
        movieCardList.add(movie)
        notifyDataSetChanged()
    }
}