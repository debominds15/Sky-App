package com.sky.demo.presentation.movie_list

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sky.demo.R
import com.sky.demo.databinding.FragmentListBinding
import com.sky.demo.presentation.movie_list.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private lateinit var adapter: MovieAdapter
    private val movieListViewModel by viewModels<MovieListViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        init()
        return binding.root

    }

    private fun init() {
        adapter = MovieAdapter(listOf())
        updateAsPerOrientation(resources.configuration.orientation)

        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        movieListViewModel.filterMovies(text, movieListViewModel.movies.value ?: emptyList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListViewModel.movies.observe(viewLifecycleOwner) {
            adapter = MovieAdapter(it)
            binding.recyclerMovie.adapter = adapter
        }

        movieListViewModel.dataLoading.observe(viewLifecycleOwner) {
            when (it) {
                false -> binding.progressBar.visibility = View.GONE
                else -> binding.progressBar.visibility = View.VISIBLE
            }
        }

        movieListViewModel.error.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvError.text = String.format(getString(R.string.txt_try_again), it)
            }
        }

        movieListViewModel.filteredMovies.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateAsPerOrientation(newConfig.orientation)
    }

    private fun updateAsPerOrientation(newConfig: Int) {
        when (newConfig) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 2)
                binding.recyclerMovie.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 3)
                binding.recyclerMovie.layoutManager = layoutManager
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}