package net.paulacr.fluffycat.ui.catdashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard_main.*
import net.paulacr.fluffycat.R
import net.paulacr.fluffycat.model.CatImage
import net.paulacr.fluffycat.model.Pet
import net.paulacr.fluffycat.ui.pagination.PaginationManager
import org.koin.android.viewmodel.ext.android.viewModel

class CatDashboardActivity : AppCompatActivity(), PaginationManager.OnScrollMore {

    private val petViewModel: CatDashboardViewModel by viewModel()
    private val cats = mutableListOf<CatImage>()
    private lateinit var adapter: CatDashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)

        setupRecyclerView(emptyList())
        petViewModel.getCats()
        petViewModel.onDataReady.observe(this) {
            adapter.updateItems(it)
        }

        setAnimation()
    }

    private fun setAnimation() {
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }

    private fun setupRecyclerView(pets: List<CatImage>) {
        val layoutManager = StaggeredGridLayoutManager(DEFAULT_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        val endlessScroll = PaginationManager(layoutManager)
        cats.addAll(pets)
        adapter = CatDashboardAdapter(cats)

        rvPetDashboard.layoutManager = layoutManager
        rvPetDashboard.adapter = adapter

        rvPetDashboard.addOnScrollListener(endlessScroll)
        endlessScroll.setListenerScroll(this)
    }

    private fun mockedPet(): List<Pet> {
        return listOf(
            Pet("Gato chique", "cat", "1", "white", R.drawable.cat_1),
            Pet("Gato chique", "cat", "1", "white", R.drawable.dog_4),
            Pet("Gato chique", "cat", "1", "white", R.drawable.cat_3),

            Pet("Gato chique", "cat", "1", "white", R.drawable.dog_2),
            Pet("Gato chique", "cat", "1", "white", R.drawable.rat_1),
            Pet("Gato chique", "cat", "1", "white", R.drawable.cat_4),
            Pet("Gato chique", "cat", "1", "white", R.drawable.dog_3),
            Pet("Gato chique", "cat", "1", "white", R.drawable.cat_2),
            Pet("Gato chique", "cat", "1", "white", R.drawable.dog_1)

        )
    }

    override fun onScrollMorePages(page: Int) {
        petViewModel.getCats()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in_return)
    }

    companion object {

        private const val DEFAULT_SPAN_COUNT = 2

        fun newIntent(context: Context): Intent {
            return Intent(context, CatDashboardActivity::class.java)
        }
    }
}