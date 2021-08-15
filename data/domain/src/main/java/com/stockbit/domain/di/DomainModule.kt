package com.stockbit.domain.di

import com.stockbit.domain.usecase.CryptoUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CryptoUseCase(get()) }
}