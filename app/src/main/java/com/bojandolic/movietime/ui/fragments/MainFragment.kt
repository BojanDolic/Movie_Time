package com.bojandolic.movietime.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bojandolic.movietime.R
import com.bojandolic.movietime.databinding.FragmentMainBinding
import com.bojandolic.movietime.ui.adapters.MoviesRecyclerAdapter
import com.bojandolic.movietime.ui.viewmodels.MainFragmentViewModel
import com.bojandolic.movietime.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        binding.toolbar.inflateMenu(R.menu.toolbar_search_menu)
        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }


        if(viewmodel.movies.value == null)
            viewmodel.getTopRatedMovies()
        viewmodel.movies.observe(viewLifecycleOwner) { movieResponse ->

            Log.d("TAG", "onViewCreated: PROMJENA DOGAĐAJ")

            when(movieResponse.status) {
                Resource.Status.SUCCESS -> {
                    val movies = movieResponse.data?.movies
                    val adapter = MoviesRecyclerAdapter()
                    adapter.asyncDiff.submitList(movies?.take(10))

                    Log.d("TAG", "onViewCreated: ${adapter.asyncDiff.currentList.size}")

                    binding.moviesRecycler.apply {
                        setAdapter(adapter)
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "onViewCreated: ${movieResponse.message}")
                }
                Resource.Status.LOADING -> {
                    Log.e("TAG", "DATA IS LOADING")
                }
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}