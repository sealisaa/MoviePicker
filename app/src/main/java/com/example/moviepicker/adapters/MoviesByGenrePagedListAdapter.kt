package com.example.moviepicker.adapters

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepicker.R
import com.example.moviepicker.data.model.movie.FilmItem
import com.example.moviepicker.data.repository.NetworkState

class MoviesByGenrePagedListAdapter(val fragment: Fragment) :
    PagedListAdapter<FilmItem, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val DATA_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position))
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        if (viewType == DATA_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.vertical_movie_card, parent, false)
            return MovieItemViewHolder(view, fragment)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            DATA_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<FilmItem>() {
        override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(view: View, val fragment: Fragment) : RecyclerView.ViewHolder(view) {

        fun bind(movie: FilmItem?) {
            val title: TextView = itemView.findViewById(R.id.movieTitle)
            val year: TextView = itemView.findViewById(R.id.movieYear)
            val poster: ImageView = itemView.findViewById(R.id.moviePoster)
            val bundle = Bundle()

            title.text = movie?.nameRu
            year.text = movie?.year
            Glide.with(fragment).load(movie?.posterUrl).into(poster)

            itemView.setOnClickListener {
                if (movie != null) {
                    bundle.putInt("movieId", movie.kinopoiskId)
                }
                fragment.findNavController().navigate(R.id.descriptionFragment, bundle)
            }
        }
    }

    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {

            val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
            val errorMessage: TextView = itemView.findViewById(R.id.textViewConnectionGenreMovies)
            if (networkState != null && networkState == NetworkState.LOADING) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

//            val errorMessage: TextView = itemView.findViewById(R.id.textViewConnectionGenreMovies)
            if (networkState != null && networkState == NetworkState.ERROR) {
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = networkState.msg
            } else {
                errorMessage.visibility = View.GONE
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}