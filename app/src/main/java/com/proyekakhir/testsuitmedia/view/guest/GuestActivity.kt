package com.proyekakhir.testsuitmedia.view.guest

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.proyekakhir.core.adapter.GuestAdapter
import com.proyekakhir.core.adapter.GuestLoadStateAdapter
import com.proyekakhir.testsuitmedia.databinding.ActivityGuestBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuestActivity : AppCompatActivity() {

    private var _binding: ActivityGuestBinding? = null
    private val binding get() = _binding!!
    private val adapter: GuestAdapter by inject()
    private val viewModel: GuestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Guest"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.proyekakhir.testsuitmedia.R.mipmap.ic_back_white)

        binding.swipeRefresh.setOnRefreshListener {
            getData()
        }
        getData()
        binding.rcv.layoutManager = GridLayoutManager(this@GuestActivity, 2)

        binding.rcv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GuestLoadStateAdapter { adapter.retry() },
            footer = GuestLoadStateAdapter { adapter.retry() }
        )

        viewModel.guest.observe(this) {
            showData()
            adapter.submitData(lifecycle, it)
            with(binding) {
                rcv.adapter = adapter
            }
        }

        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }


    }

    private fun getData() {
        lifecycleScope.launch {
            showLoading()
            delay(2000)
            viewModel.getGuest()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading() {
        with(binding) {
            rcv.visibility = View.GONE
            shimmmers.visibility = View.VISIBLE
            shimmmers.startShimmer()
        }
    }

    private fun showData() {
        with(binding) {
            swipeRefresh.isRefreshing = false
            rcv.visibility = View.VISIBLE
            shimmmers.stopShimmer()
            shimmmers.visibility = View.GONE
        }
    }

}