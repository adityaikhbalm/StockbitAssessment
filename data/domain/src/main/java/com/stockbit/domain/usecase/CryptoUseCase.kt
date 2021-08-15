package com.stockbit.domain.usecase

import com.stockbit.model.SubscribeModel
import com.stockbit.repository.CryptoRepository

class CryptoUseCase(private val repository: CryptoRepository) {

    suspend fun fetchTopTier() = repository.fetchTopTier()

    fun subscribeCoinList(subscribeModel: SubscribeModel) =
        repository.subscribeCoinList(subscribeModel)

    fun observeCoinList() = repository.observeCoinList()
}