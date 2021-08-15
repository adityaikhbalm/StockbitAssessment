package com.stockbit.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.stockbit.common.base.BaseViewModel
import com.stockbit.common.utils.Event
import com.stockbit.domain.usecase.CryptoUseCase
import com.stockbit.features.home.R
import com.stockbit.model.CoinInfo
import com.stockbit.model.SubscribeModel
import com.stockbit.model.TickerModel
import com.stockbit.repository.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.filter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val cryptoUseCase: CryptoUseCase
) : BaseViewModel() {

    init {
        fetchTopTier()
    }

    private val _listCrypto = MutableLiveData<Resource<List<CoinInfo>>>()
    val listCrypto: LiveData<Resource<List<CoinInfo>>>
        get() = _listCrypto

    fun fetchTopTier() {
        viewModelScope.launch {
            cryptoUseCase.fetchTopTier()
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    if (it.status == Resource.Status.SUCCESS) {
                        _listCrypto.value = Resource.success(it.data?.data)
                        it.data?.data?.let { data -> subscribeCoinList(data) }
                    }
                    else if (it.status == Resource.Status.ERROR)
                        _snackbarError.value = Event(R.string.an_error_happened)
                }
        }
    }

    private fun subscribeCoinList(data: List<CoinInfo>) {
        val subs = mutableListOf<String>()
        data.forEach {
            it.coinInfo.name?.let { name ->
                subs.add("5~CCCAGG~$name~USD")
            }
        }
        val subscribeModel = SubscribeModel(
            action = "SubAdd",
            subs = subs
        )
        cryptoUseCase.subscribeCoinList(subscribeModel)
    }
    
    val observeCoinList by lazy {
        cryptoUseCase.observeCoinList()
            .flowOn(Dispatchers.IO)
            .filter { it.type == 5 }
            .asLiveData()
    }
}