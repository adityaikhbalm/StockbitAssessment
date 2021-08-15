package com.stockbit.hiring.di

import com.stockbit.domain.di.domainModule
import com.stockbit.features.home.di.homeModule
import com.stockbit.local.di.localModule
import com.stockbit.remote.di.remoteModule
import com.stockbit.repository.di.repositoryModule

val appComponent = listOf(
    domainModule,
    repositoryModule,
    remoteModule,
    localModule,
    homeModule
)