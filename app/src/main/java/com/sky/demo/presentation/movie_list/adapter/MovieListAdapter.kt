package com.sky.demo.presentation.movie_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sky.demo.R
import com.sky.demo.databinding.ItemMovieBinding
import com.sky.domain.entities.Film

class MovieAdapter(private var movies: List<Film>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateList(list: List<Film>) {
        movies = list
        notifyDataSetChanged()
    }

    class MovieViewHolder(private val itemView: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val poster: ImageView = itemView.findViewById(R.id.img_movie)
        fun bind(movie: Film) {
            title.text = movie.title
            Glide.with(itemView.context)
                .load(movie.poster)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_image_24_place_holder)
                .into(poster)

        }
    }
}