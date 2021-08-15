package com.stockbit.repository

import com.stockbit.local.dao.ExampleDao
import com.stockbit.model.CoinResponse
import com.stockbit.model.SubscribeModel
import com.stockbit.model.TickerModel
import com.stockbit.remote.RemoteDatasource
import com.stockbit.repository.utils.Resource
import com.stockbit.repository.utils.fetch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOn

interface CryptoRepository {
    suspend fun fetchTopTier(): Flow<Resource<CoinResponse>>
    fun subscribeCoinList(subscribeModel: SubscribeModel)
    fun observeCoinList() : Flow<TickerModel>
}

class CryptoRepositoryImpl(
    private val appDispatchers: AppDispatchers,
    private val datasource: RemoteDatasource,
    private val dao: ExampleDao
): CryptoRepository {

    override suspend fun fetchTopTier() =
        fetch {
            datasource.fetchTopTier()
        }.flowOn(appDispatchers.io)

    override fun subscribeCoinList(subscribeModel: SubscribeModel) =
        datasource.subscribeCoinList(subscribeModel)

    override fun observeCoinList(): Flow<TickerModel> {
        return datasource.observeCoinList().consumeAsFlow()
    }
}