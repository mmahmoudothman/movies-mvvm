package io.ahmed56734.movies.util

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import io.ahmed56734.movies.repository.NetworkState
import io.ahmed56734.movies.repository.Status




@BindingAdapter("posterImage")
fun loadPoster(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("isRefreshing")
fun showSwipeRefreshLayoutLoading(swipeRefreshLayout: SwipeRefreshLayout, networkState: NetworkState?) {
    when (networkState?.status) {
        Status.RUNNING -> swipeRefreshLayout.isRefreshing = true
        else -> swipeRefreshLayout.isRefreshing = false
    }
}

//@BindingAdapter("srcCompat")
//fun bindSrcCompat(imageView: AppCompatImageView, drawable:Drawable){
//    imageView.setImageDrawable(drawable)
//}

