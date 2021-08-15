package com.stockbit.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.stockbit.model.CoinModel

@Dao
abstract class ExampleDao: BaseDao<CoinModel>() {

    suspend fun save(data: CoinModel) {
        insert(data)
    }

    suspend fun save(datas: List<CoinModel>) {
        insert(datas)
    }
}