package com.anikinkirill.foodrecipes.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anikinkirill.foodrecipes.responses.ApiResponse

// CacheObject: Type for the Resource data. (cache database)
// RequestObject: Type for the API response. (api request result)
abstract class NetworkBoundResource<CacheObject, RequestObject>(private val appExecutors: AppExecutors) {

    companion object {
        private const val TAG = "NetworkBoundResource"
    }

    private var results = MediatorLiveData<Resource<CacheObject>>()

    init {
        // 1. update LiveData for loading status
        results.value = Resource.Loading()
        // 2. observe LiveData from the local database
        val localDatabaseSource: LiveData<CacheObject> = loadFromDb()
        results.addSource(localDatabaseSource) { it1 ->
            results.removeSource(localDatabaseSource)
            if(shouldFetch(it1)){
                // get data from the network
                fetchFromNetwork(localDatabaseSource)
            }else {
                results.addSource(localDatabaseSource) { it2 ->
                    setValue(Resource.Success(it2))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<CacheObject>){
        if(results.value != newValue) {
            results.value = newValue
        }
    }

    /**
     * 1) observe local database
     * 2) if <condition/> query the network
     * 3) stop observing local database
     * 4) insert new data from network to cache
     * 5) start observing local database again to see refreshed data from the network
     */

    private fun fetchFromNetwork(dbSource: LiveData<CacheObject>) {
        results.addSource(dbSource) {
            // loading status
            results.value = Resource.Loading(it)
        }
        val apiResponse = createCall()
        results.addSource(apiResponse) {
            results.removeSource(dbSource)
            results.removeSource(apiResponse)

            when(it) {
                is ApiResponse.ApiSuccessResponse -> {
                    Log.d(TAG, "fetchFromNetwork: ApiSuccessResponse")
                    appExecutors.getDiskIO().execute {
                        // save result to the local database
                        saveCallResult(processResponse(it))
                        // observe local database again
                        appExecutors.getMainThreadExecutor().execute {
                            results.addSource(loadFromDb()) { cacheObject ->
                                results.value = Resource.Success(cacheObject)
                            }
                        }
                    }
                }
                is ApiResponse.ApiErrorResponse -> {
                    Log.d(TAG, "fetchFromNetwork: ApiErrorResponse")
                    appExecutors.getMainThreadExecutor().execute {
                        results.addSource(loadFromDb()) { apiErrorResponseCacheObject ->
                            results.value = Resource.Success(apiErrorResponseCacheObject)
                        }
                    }
                }
                is ApiResponse.ApiEmptyResponse -> {
                    Log.d(TAG, "fetchFromNetwork: ApiEmptyResponse")
                    results.addSource(dbSource) { apiErrorResponseCacheObject ->
                        results.value = Resource.Error(null, "something went wrong")
                    }
                }
            }

        }
    }

    private fun processResponse(response: ApiResponse.ApiSuccessResponse<RequestObject>) : RequestObject {
        return response.body
    }

    // Called to save the result of the API response into the database
    protected abstract fun saveCallResult(item: RequestObject)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    protected abstract fun shouldFetch(data: CacheObject?): Boolean

    // Called to get the cached data from the database.
    protected abstract fun loadFromDb(): LiveData<CacheObject>

    // Called to create the API call.
    protected abstract fun createCall(): LiveData<ApiResponse<RequestObject>>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected open fun onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun asLiveData(): LiveData<Resource<CacheObject>> = results
}
