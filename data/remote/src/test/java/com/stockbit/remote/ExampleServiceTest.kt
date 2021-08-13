package com.stockbit.remote

import com.stockbit.remote.base.BaseTest
import org.junit.Test
import retrofit2.HttpException

class ExampleServiceTest: BaseTest() {

    @Test
    fun `get example success`() {

    }

    @Test(expected = HttpException::class)
    fun `get example fail`() {

    }
}