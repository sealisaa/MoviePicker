package com.example.moviepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


class GenresAdapter : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {
    var genreTitles = arrayOf(
        "Мультфильмы", "Триллеры", "Комедии", "Ужасы"
    )
    var genreCovers = intArrayOf(
        R.drawable.cartoon,
        R.drawable.thriller,
        R.drawable.comedy,
        R.drawable.horror
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresAdapter.GenresViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.genre_card, parent, false)
        return GenresViewHolder(v)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return genreTitles.size
    }

    inner class GenresViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var genreButton: Button

        init {
            genreButton = view.findViewById(R.id.genreButton)
            view.setOnClickListener(this)
        }

        fun bindView(position: Int) {
            genreButton.text = genreTitles[position]
            genreButton.setBackgroundResource(genreCovers[position])
        }

        override fun onClick(view: View) {
            // TODO: implement genre buttons onClick
        }
    }
}