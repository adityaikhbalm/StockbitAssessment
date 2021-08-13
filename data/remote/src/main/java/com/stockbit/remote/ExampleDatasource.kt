package com.stockbit.remote

/**
 * Implementation of [ExampleService] interface
 */
class ExampleDatasource(private val exampleService: ExampleService) {

    fun fetchTopUsersAsync() =
            exampleService.fetchExampleAsync()

}