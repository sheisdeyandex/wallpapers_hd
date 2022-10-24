package com.ivan.wallpapers.dry

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewScrollToEnd {
    fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit){

        this.addOnScrollListener(object: RecyclerView.OnScrollListener(){

            private val VISIBLE_THRESHOLD = 5

            private var loading = true
            private var previousTotal = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView,
                                              newState: Int) {

                with(layoutManager as GridLayoutManager){

                    val visibleItemCount = childCount
                    val totalItemCount = itemCount
                    val firstVisibleItem = findFirstVisibleItemPosition()

                    if (loading && totalItemCount > previousTotal){

                        loading = false
                        previousTotal = totalItemCount
                    }

                    if(!loading && (totalItemCount - visibleItemCount-7) <= (firstVisibleItem + VISIBLE_THRESHOLD)){
                        onScrolledToEnd()
                        loading = true
                    }
                }
            }
        })
    }
}