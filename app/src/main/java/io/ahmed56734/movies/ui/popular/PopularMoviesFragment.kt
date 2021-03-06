package io.ahmed56734.movies.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.ahmed56734.movies.databinding.FragmentPopularMoviesBinding
import io.ahmed56734.movies.ui.adapters.MoviesAdapter
import io.ahmed56734.movies.ui.base.BaseFragment
import io.ahmed56734.movies.util.Status
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class PopularMoviesFragment : BaseFragment() {


    private val viewModel: PopularMoviesViewModel by viewModel()
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }
    private lateinit var binding: FragmentPopularMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.pagedList.observe(this, Observer {
            moviesAdapter.submitList(it)
        })

        viewModel.refreshState.observe(this, Observer {
            binding.networkState = it

            when (it?.status) {
                Status.RUNNING -> showErrorView(false)
                Status.SUCCESS -> showErrorView(false)
                Status.FAILED -> {
                    if(moviesAdapter.itemCount == 0)
                        showErrorView(true)
                }
            }
        })

        viewModel.snackBarEvents.observe(this, Observer {
            it.getContentIfNotHandled()?.run {
                Snackbar.make(coordinatorLayout, this, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter.apply {
            onMovieClicked = {
                findNavController().navigate(
                    PopularMoviesFragmentDirections
                        .actionPopularMoviesFragmentToMovieDetailsFragment(it.id)
                )
            }
            onBookmarkClicked = {
                viewModel.toggleFavorites(it)
            }
        }

        moviesRecyclerView.apply {
            adapter = moviesAdapter
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }//recyclerview setup

        binding.mainSwipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }


    }


}
