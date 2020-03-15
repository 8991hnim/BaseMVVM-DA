package m.tech.basemvvm.api

import androidx.lifecycle.LiveData
import m.tech.basemvvm.models.Post
import m.tech.basemvvm.util.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): LiveData<ApiResponse<List<Post>>>
}