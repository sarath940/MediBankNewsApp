package com.example.medibanknewsapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestCoroutineRule2 : TestWatcher() {

    val testDispatcher = TestCoroutineDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description?) {
        if (description != null) {
            super.starting(description)
        }
        Dispatchers.setMain(testDispatcher)
        //Dispatchers.setIODispatcher(testDispatcher)  // Add this line to set IO dispatcher
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description?) {
        if (description != null) {
            super.finished(description)
        }
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testDispatcher.runBlockingTest { block() }
    }
}