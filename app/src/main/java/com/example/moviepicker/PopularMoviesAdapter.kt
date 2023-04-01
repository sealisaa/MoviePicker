package com.example.moviepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesAdapter.PopularMoviesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        return PopularMoviesViewHolder(v)
    }

    override fun onBindViewHolder(holder: PopularMoviesAdapter.PopularMoviesViewHolder, position: Int) {
//        holder.titleView.text = titles[position]
//        holder.yearView.text = years[position]
    }

    override fun getItemCount(): Int {
        return 100
    }

    inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var titleView: TextView
//        var yearView: TextView
//
//        init {
//            titleView = itemView.findViewById(R.id.movieTitle)
//            yearView = itemView.findViewById(R.id.movieYear)
//        }
    }
}