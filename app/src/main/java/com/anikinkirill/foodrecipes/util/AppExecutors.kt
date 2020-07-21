package com.anikinkirill.foodrecipes.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors private constructor() {

    companion object {
        val instance: AppExecutors by lazy {
            AppExecutors()
        }
    }

    private val mDiskIO = Executors.newSingleThreadExecutor() // for cache

    private val mMainThreadExecutor = MainThreadExecutor()

    inner class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    fun getDiskIO() = mDiskIO
    fun getMainThreadExecutor() = mMainThreadExecutor

}