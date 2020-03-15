package m.tech.basemvvm.util

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * This method performs a number of checks and then returns the Response type for the Retrofit requests.
 * (@bodyType is the ResponseType)
 * <p>
 * CHECK #1) returnType returns LiveData
 * CHECK #2) Type LiveData<T> is of ApiResponse.class
 * CHECK #3) Make sure ApiResponse is parameterized. AKA: ApiResponse<T> exists.
 */

class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        //Check #1
        //Make sure the Call Adapter is returning a type of LiveData
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        //Check #2
        //Type that LiveData is wrapping
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)

        //Check if it's of Type ApiResponse
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }

        //Check #3
        //Check if ApiResponse is parameterized. AKA: Does ApiResponse<T> exists? (Must wrap around T)
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)

        return LiveDataCallAdapter<Any>(bodyType)
    }
}