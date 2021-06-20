package com.bojandolic.movietime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bojandolic.movietime.databinding.DetailsFragmentBinding
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.ui.viewmodels.DetailsViewModel
import com.bojandolic.movietime.utilities.loadMovieUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie

        with(binding) {
            movieImage.loadMovieUrl(movie.backdropImage, Movie.IMAGE_W780)
            backgroundMovieImage.loadMovieUrl(movie.backdropImage, Movie.IMAGE_ORIGINAL)
            movieTitle.text = movie.title
            movieDesc.text = movie.description
        }



    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}