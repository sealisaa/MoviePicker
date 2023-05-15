package com.example.moviepicker.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepicker.R
import com.example.moviepicker.data.model.movie.Film

class FavouritesAdapter(val fragment: Fragment, var favouriteMoviesList: List<Film>) :
    RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {
    fun updateList(newList: List<Film>) {
        favouriteMoviesList = newList
        notifyDataSetChanged() //or you can implement a DiffUtil.Callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_movie_card, parent, false)
        return FavouritesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return favouriteMoviesList.size
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        // todo implement network error message as it is done in MoviesPagedListAdapter
        holder.bindView(position)
        holder.movieCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("movieId", favouriteMoviesList[position].data.kinopoiskId)
            fragment.findNavController().navigate(R.id.descriptionFragment, bundle)
        }
    }

    inner class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var movieCard: View
        var movieTitle: TextView
        var moviePoster: ImageView
        var movieRating: TextView
        var movieDirector: TextView

        init {
            movieCard = view.findViewById(R.id.movieCard)
            movieTitle = view.findViewById(R.id.movieTitle)
            moviePoster = view.findViewById(R.id.moviePoster)
            movieRating = view.findViewById(R.id.movieRating)
            movieDirector = view.findViewById(R.id.directorName)
            view.setOnClickListener(this)
        }

        fun bindView(position: Int) {
            Glide.with(fragment).load(favouriteMoviesList[position].data.posterUrl).into(moviePoster)
            movieTitle.text = favouriteMoviesList[position].data.nameEn
            movieRating.text = favouriteMoviesList[position].rating?.rating.toString()
            movieDirector.text = favouriteMoviesList[position].data.year
        }

        override fun onClick(view: View) {
            // TODO: implement genre buttons onClick
        }
    }

}