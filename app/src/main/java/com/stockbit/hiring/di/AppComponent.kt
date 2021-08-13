package com.stockbit.hiring.di

import com.stockbit.local.di.localModule
import com.stockbit.remote.di.createRemoteModule
import com.stockbit.repository.di.repositoryModule

val appComponent = listOf(createRemoteModule("https://api.github.com/"), repositoryModule, localModule)