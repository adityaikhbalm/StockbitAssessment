package com.stockbit.remote

import com.stockbit.model.SubscribeModel
import com.stockbit.model.TickerModel
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface SocketService {
    @Send
    fun subscribeCoinList(subscribeModel: SubscribeModel)

    @Receive
    fun observeCoinList() : ReceiveChannel<TickerModel>
}