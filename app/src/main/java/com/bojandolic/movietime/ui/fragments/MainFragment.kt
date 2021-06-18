package com.bojandolic.movietime.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bojandolic.movietime.R
import com.bojandolic.movietime.databinding.FragmentMainBinding
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.ui.adapters.MoviesRecyclerAdapter
import com.bojandolic.movietime.ui.viewmodels.MainFragmentViewModel
import com.bojandolic.movietime.utilities.Resource
import com.bojandolic.movietime.utilities.onTextChangeWaitListener
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: MainFragmentViewModel by viewModels()

    val seconds = 1000

    var searching = false

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.buttonToggles.addOnButtonCheckedListener { group, checkedId, isChecked ->

            viewmodel.category.value = group.findViewById<MaterialButton>(group.checkedButtonId).tag.toString()
            Log.d(TAG, "onViewCreated: ${viewmodel.category.value}")
            //category.switc
        }

        val adapter = MoviesRecyclerAdapter()

        viewmodel.allMovies.observe(viewLifecycleOwner) { resource ->

            Log.d(TAG, "onViewCreated: PROMJENA DOGAĐAJ")

            resource?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val movies = resource.data?.movies ?: emptyList()

                        adapter.submitList(movies?.take(10))


                        binding.moviesRecycler.apply {
                            setAdapter(adapter)
                            layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                    Resource.Status.ERROR -> {
                        Log.e(TAG, "onViewCreated: ${resource.message}")
                    }
                    Resource.Status.LOADING -> {
                        Log.e(TAG, "DATA IS LOADING")
                    }
                }
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    @ExperimentalCoroutinesApi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search_menu, menu)

        Log.d(TAG, "onOptionsItemSelected: POZVAN ONCREATEOPTIONSMENU")

        val searchItem = menu.findItem(R.id.movie_search)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                viewmodel.isSearching = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewmodel.isSearching = false
                viewmodel.searchQuery2.value = ""
                binding.moviesRecycler.scrollToPosition(0)
                return true
            }
        })


        val searchView = searchItem.actionView as SearchView

        if (viewmodel.isSearching) {
            searchItem.expandActionView()
            searchView.setQuery(viewmodel.searchQuery2.value, false)
            searchView.clearFocus()
        }

        searchView.onTextChangeWaitListener()
            .debounce(1000)
            .onEach { searchQuery ->
                viewmodel.searchQuery2.value = searchQuery
            }
            .launchIn(lifecycleScope)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}