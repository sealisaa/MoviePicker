package com.example.moviepicker

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {
    var popularMoviesTitles = arrayOf(
        "Звёздные войны: Эпизод 1 — Скрытая угроза",
        "Работа без авторства", "Список Шиндлера", "Великий Гэтсби",
        "Паразиты", "Твое имя", "Тайна Коко",
        "Вечное сияние чистого разума", "Дьявол в деталях", "Сияние",
        "Звёздные войны: Эпизод 1 — Скрытая угроза",
        "Работа без авторства", "Список Шиндлера", "Великий Гэтсби",
        "Паразиты", "Твое имя", "Тайна Коко",
        "Вечное сияние чистого разума", "Дьявол в деталях", "Сияние"
    )

    var popularMoviesYears = arrayOf(
        "1999",
        "2018", "1993", "2013",
        "2019", "2016", "2017",
        "2004", "2021", "1980",
        "1999",
        "2018", "1993", "2013",
        "2019", "2016", "2017",
        "2004", "2021", "1980"
    )

    var popularMoviesPosters = intArrayOf(
        R.drawable.star_wars,
        R.drawable.poster1, R.drawable.poster2, R.drawable.poster3,
        R.drawable.poster4, R.drawable.poster5, R.drawable.poster6,
        R.drawable.poster7, R.drawable.poster8, R.drawable.poster9,
        R.drawable.star_wars,
        R.drawable.poster1, R.drawable.poster2, R.drawable.poster3,
        R.drawable.poster4, R.drawable.poster5, R.drawable.poster6,
        R.drawable.poster7, R.drawable.poster8, R.drawable.poster9
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesAdapter.PopularMoviesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vertical_movie_card, parent, false)
        return PopularMoviesViewHolder(v)
    }

    override fun onBindViewHolder(holder: PopularMoviesAdapter.PopularMoviesViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return popularMoviesTitles.size
    }

    inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleView: TextView
        var yearView: TextView
        var posterView: ImageView

        init {
            titleView = itemView.findViewById(R.id.movieTitle)
            yearView = itemView.findViewById(R.id.movieYear)
            posterView = itemView.findViewById(R.id.moviePoster)
        }

        fun bindView(position: Int) {
            titleView.text = popularMoviesTitles[position]
            yearView.text = popularMoviesYears[position]
            posterView.setImageResource(popularMoviesPosters[position])
        }
    }
}