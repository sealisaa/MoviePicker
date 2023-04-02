package com.example.moviepicker

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView


class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesAdapter.PopularMoviesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vertical_movie_card, parent, false)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 230f, parent.resources.displayMetrics
            ).toInt()
        )
        layoutParams.rightMargin = 0
        layoutParams.leftMargin = 0
        layoutParams.topMargin = 0
        layoutParams.bottomMargin = 0
        v.layoutParams = layoutParams;
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