package com.stockbit.repository.di

import com.stockbit.repository.AppDispatchers
import com.stockbit.repository.CryptoRepository
import com.stockbit.repository.CryptoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory<CryptoRepository> { CryptoRepositoryImpl(get(), get(), get()) }
}