package dev.ruisantos.criticalnews

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addEndlessScroll(callback: () -> Unit) {

    val layoutManager = this.layoutManager as GridLayoutManager
    val adapter = this.adapter

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val totalItemCount = adapter?.itemCount ?: 0

            if (lastVisibleItemPosition + 1 == totalItemCount && totalItemCount > 0) {
                callback()
            }
        }
    })
}