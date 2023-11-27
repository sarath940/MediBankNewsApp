package com.example.medibanknewsapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppCoroutineDispatchers(
    var io: CoroutineDispatcher = Dispatchers.IO,
    var default: CoroutineDispatcher = Dispatchers.Default,
    var main: CoroutineDispatcher = Dispatchers.Main
)
