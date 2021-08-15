package com.stockbit.remote

import com.stockbit.model.SubscribeModel

/**
 * Implementation of [RemoteService] interface
 */
class RemoteDatasource(
    private val remoteService: RemoteService,
    private val socketService: SocketService
) {
    suspend fun fetchTopTier() = remoteService.fetchTopTier()

    fun subscribeCoinList(subscribeModel: SubscribeModel) =
        socketService.subscribeCoinList(subscribeModel)

    fun observeCoinList() = socketService.observeCoinList()
}