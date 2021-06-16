package com.bojandolic.movietime.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bojandolic.movietime.databinding.FragmentMainBinding
import com.bojandolic.movietime.databinding.MovieItemBinding
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.utilities.loadMovieUrl

class MoviesRecyclerAdapter : ListAdapter<Movie, MoviesRecyclerAdapter.MovieViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    val asyncDiff = AsyncListDiffer(this, DiffCallback())

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = asyncDiff.currentList[position]
        holder.bindViews(movie)
    }

    override fun getItemCount(): Int = asyncDiff.currentList.size

    class MovieViewHolder(val binding: MovieItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

           fun bindViews(movie: Movie) {
               Log.d("TAG", "bindViews: MOVIE: ${movie.title}")
               binding.movieTitle.text = movie.title
               binding.movieDesc.text = movie.description

               binding.movieImage.loadMovieUrl(movie.poster)
           }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }


}