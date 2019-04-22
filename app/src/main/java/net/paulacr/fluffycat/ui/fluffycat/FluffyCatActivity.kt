package net.paulacr.fluffycat.ui.fluffycat

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_fluffy_cat.*
import net.paulacr.fluffycat.R
import net.paulacr.fluffycat.databinding.ActivityFluffyCatBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FluffyCatActivity : AppCompatActivity() {

    private val fluffyViewModel: FluffyCatActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fluffy_cat)

        applyDataBinding()
        setAnimation()
        observeActions()
    }

    private fun applyDataBinding() {
        DataBindingUtil.setContentView<ActivityFluffyCatBinding>(this, R.layout.activity_fluffy_cat).apply {
            activityViewModel = fluffyViewModel
        }
    }

    private fun observeActions() {
        fluffyViewModel.actionOnFluffyReady.observe(this) {
            Glide.with(this)
                .load(it.url)
                .placeholder(R.drawable.cat_pawprint)
                .into(catImage)
        }

        var values = ""
        fluffyViewModel.actionSendResult.observe(this) {
            Snackbar.make(moreCatFluflyButton, "Result = $it", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setAnimation() {
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in_return)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FluffyCatActivity::class.java)
        }
    }
}