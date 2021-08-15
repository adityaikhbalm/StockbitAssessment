package com.stockbit.remote

import com.stockbit.model.SubscribeModel
import com.stockbit.model.TickerModel
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

interface SocketService {
//    @Receive
//    fun observeWebSocketConnection(): Flow<WebSocket.Event>

    @Send
    fun subscribeCoinList(subscribeModel: SubscribeModel)

    @Receive
    fun observeCoinList() : ReceiveChannel<TickerModel>
}