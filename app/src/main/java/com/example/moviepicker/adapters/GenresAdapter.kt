package com.example.moviepicker.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepicker.R


class GenresAdapter(private val fragment: Fragment, private val genreTitles: MutableList<String>, private val genreCovers: MutableList<Int>) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {
//    var genreTitles = arrayOf
//    (
//        "", "Триллеры", "Комедии", "Ужасы"
//    )
//    var genreCovers = intArrayOf(
//        R.drawable.cartoon,
//        R.drawable.thriller,
//        R.drawable.comedy,
//        R.drawable.horror
//    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.genre_card, parent, false)
        return GenresViewHolder(v)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val bundle = Bundle()
        holder.bindView(position)
        holder.genreButton.setOnClickListener {
            // todo navigate to description fragment + passing movie id
//            bundle.putString("genreId", genreTitles[position])
            fragment.findNavController().navigate(R.id.genreMoviesFragment)
        }
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