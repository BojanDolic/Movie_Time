package com.bojandolic.movietime.ui.adapters

import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.bojandolic.movietime.databinding.FragmentMainBinding
import com.bojandolic.movietime.databinding.MovieItemBinding
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.utilities.loadMovieUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.timerTask

class MoviesRecyclerAdapter constructor(
    val listener: OnMovieListClick
) : ListAdapter<Movie, MoviesRecyclerAdapter.MovieViewHolder>(DiffCallback()) {

    interface OnMovieListClick {
        fun onMovieClicked(movie: Movie)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindViews(movie)

    }

    inner class MovieViewHolder(val binding: MovieItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

           fun bindViews(movie: Movie) {
               Log.d("TAG", "bindViews: MOVIE: ${movie.title}")
               binding.movieTitle.text = movie.title
               binding.movieDesc.text = movie.description

               binding.adultContent.isVisible = movie.adult

               binding.movieImage.loadMovieUrl(movie.poster, Movie.IMAGE_W500)

               binding.root.setOnClickListener {
                   listener.onMovieClicked(movie)
               }

           }


    }

    private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.movieId == newItem.movieId && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }


}