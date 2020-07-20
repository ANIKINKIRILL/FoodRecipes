package com.anikinkirill.foodrecipes.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anikinkirill.foodrecipes.responses.ApiResponse

// CacheObject: Type for the Resource data. (cache database)
// RequestObject: Type for the API response. (api request result)
abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private var results = MediatorLiveData<Resource<CacheObject>>()

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
