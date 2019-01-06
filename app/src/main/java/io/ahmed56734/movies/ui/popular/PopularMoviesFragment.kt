package io.ahmed56734.movies.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.ahmed56734.movies.MoviesAdapter
import io.ahmed56734.movies.databinding.FragmentPopularMoviesBinding
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = PopularMoviesFragment()
    }

    private val viewModel: PopularMoviesViewModel by viewModel()
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }
    private lateinit var binding: FragmentPopularMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.pagedList.observe(this, Observer {
            moviesAdapter.submitList(it)
        })

        viewModel.refreshState.observe(this, Observer {
            if(it == null)
                return@Observer

            binding.networkState = it
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRecyclerView.apply {
            adapter = moviesAdapter
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }//recyclerview setup


    }


}
