package com.stockbit.features.home.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.extension.hide
import com.stockbit.common.extension.show
import com.stockbit.common.extension.viewBinding
import com.stockbit.features.home.viewmodel.HomeViewModel
import com.stockbit.features.home.R
import com.stockbit.features.home.adapter.HomeAdapter
import com.stockbit.features.home.databinding.FragmentHomeBinding
import com.stockbit.model.CoinInfo
import com.stockbit.repository.utils.Resource

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class, R.layout.fragment_home),
    SwipeRefreshLayout.OnRefreshListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val homeAdapter by lazy { HomeAdapter() }
    private lateinit var lm: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) showShimmerEffect()
        setSwipeRefresh()
        setCustomCard()
        setRecyclerView()
    }

    private fun showShimmerEffect() {
        binding.recyclerContent.hide()
        binding.recyclerShimmer.show()
        binding.recyclerShimmer.showShimmerAdapter()
    }

    private fun hideShimmerEffect() {
        binding.recyclerShimmer.hideShimmerAdapter()
        binding.recyclerShimmer.hide()
        binding.recyclerContent.show()
    }

    private fun setSwipeRefresh() {
        binding.refreshLayout.setOnRefreshListener(this)
    }

    private fun setRecyclerView() {
        lm = LinearLayoutManager(context)
        binding.recyclerContent.run {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = homeAdapter
        }
        binding.recyclerContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                binding.refreshLayout.isEnabled = lm.findFirstVisibleItemPosition() == 0
            }
        })
        viewModel.listCrypto.observe(viewLifecycleOwner) {
            if (it.status == Resource.Status.SUCCESS) {
                hideShimmerEffect()
                homeAdapter.submitList(it.data)
            }
        }
        viewModel.observeCoinList.observe(viewLifecycleOwner) {
            if (it.openDay > 0) homeAdapter.openDay[it.symbol] = it.openDay
            homeAdapter.updatePrice(it)
        }
    }

    private fun setCustomCard() {
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
        shapeAppearanceModel.setTopLeftCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 80F })
        shapeAppearanceModel.setTopRightCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 80F })
        binding.cardContent.shapeAppearanceModel = shapeAppearanceModel.build()
    }

    override fun onRefresh() {
        binding.refreshLayout.isRefreshing = false
        homeAdapter.submitList(null)
        showShimmerEffect()
        viewModel.fetchTopTier()
    }
}