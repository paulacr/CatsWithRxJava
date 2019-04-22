package net.paulacr.fluffycat.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.paulacr.fluffycat.R
import net.paulacr.fluffycat.databinding.ActivityHomeBinding
import net.paulacr.fluffycat.ui.catdashboard.CatDashboardActivity
import net.paulacr.fluffycat.ui.fluffycat.FluffyCatActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeActivityViewModel: HomeActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        applyDataBinding()
        observeActions()
    }

    private fun applyDataBinding() {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
            .apply {
                activityViewModel = homeActivityViewModel
            }
    }

    private fun observeActions() {
        homeActivityViewModel.actionClickedCatDashboard.observe(this) {
            startActivity(CatDashboardActivity.newIntent(this))
        }

        homeActivityViewModel.actionClickedFluffyCat.observe(this) {
            startActivity(FluffyCatActivity.newIntent(this))
        }
    }
}