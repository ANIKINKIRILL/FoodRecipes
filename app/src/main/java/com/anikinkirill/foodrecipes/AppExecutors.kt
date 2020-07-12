package com.anikinkirill.foodrecipes

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors private constructor() {

    companion object {
        val instance: AppExecutors by lazy {
            AppExecutors()
        }
    }

    private val networkIO = Executors.newScheduledThreadPool(3)

    fun getNetworkIO() : ScheduledExecutorService {
        return networkIO
    }

}