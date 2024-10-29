package com.airwallex.core.network.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DataSource {
    val dispatcher: CoroutineDispatcher
}

abstract class RemoteDataSource(
        override val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource

abstract class LocalDataSource(
        override val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource
