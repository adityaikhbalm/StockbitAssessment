package com.stockbit.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stockbit.common_test.rules.CoroutinesMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class ExampleRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    @Before
    fun setUp() {

    }

}