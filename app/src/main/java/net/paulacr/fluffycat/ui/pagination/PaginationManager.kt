package net.paulacr.fluffycat.ui.pagination

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

class PaginationManager(private val layoutManager: StaggeredGridLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var currentPage = 1
    private lateinit var listener: OnScrollMore

    interface OnScrollMore {
        fun onScrollMorePages(page: Int)
    }

    fun setListenerScroll(instance: OnScrollMore) {
        this.listener = instance
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val visibleItemCount = recyclerView.childCount ?: 0
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPositions(null)[0]

        if (totalItemCount > previousTotal) {
            loading = false
            previousTotal = totalItemCount
        }

        val visibleThreshold = VISIBLE_THRESHOLD
        if (!loading && (totalItemCount - visibleItemCount)
            <= (firstVisibleItem + visibleThreshold)) {
            currentPage++
            listener.onScrollMorePages(currentPage)
            loading = true
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}