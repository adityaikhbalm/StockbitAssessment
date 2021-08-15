package com.stockbit.repository

import android.util.Log
import com.stockbit.local.dao.ExampleDao
import com.stockbit.model.CoinModel
import com.stockbit.model.CoinResponse
import com.stockbit.model.SubscribeModel
import com.stockbit.model.TickerModel
import com.stockbit.remote.RemoteDatasource
import com.stockbit.repository.utils.Resource
import com.stockbit.repository.utils.fetch
import com.stockbit.repository.utils.retryConnection
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.filter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException

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