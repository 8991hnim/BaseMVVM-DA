package m.tech.basemvvm.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job
import m.tech.basemvvm.SessionManager
import m.tech.basemvvm.api.ApiService
import m.tech.basemvvm.models.Post
import m.tech.basemvvm.persistence.PostDao
import m.tech.basemvvm.util.AbsentLiveData
import m.tech.basemvvm.util.ApiResponse
import m.tech.basemvvm.util.DataState
import javax.inject.Inject

class PostRepository
@Inject
constructor(
    val sessionManager: SessionManager,
    val postDao: PostDao,
    val apiService: ApiService
) : JobManager("PostRepository") {

    /*
     Make a request and save response to db

    */
    fun getPosts(): LiveData<DataState<List<Post>>>{
        return object : NetworkBoundResource<List<Post>, List<Post>>(sessionManager){
            override fun createReturnedObjectFromApi(response: List<Post>): List<Post> {
                //do not care if shouldLoadFromDb is true
                return emptyList()
            }

            override fun saveCallResult(response: List<Post>) {
                postDao.insertPosts(response)
            }

            override fun shouldLoadFromDb(): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<List<Post>>> {
                return apiService.getPosts()
            }

            override fun shouldFetch(data: List<Post>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Post>> {
                return postDao.getPosts()
            }

            override fun setJob(job: Job) {
                addJob("getPosts", job)
            }

        }.asLiveData()
    }

    /*
      Make a request no need to save response data to db (like POST request)

   */
    fun getPostsDoNotSaveDb(): LiveData<DataState<Any>>{
        return object : NetworkBoundResource<List<Post>, Any>(sessionManager){
            override fun createReturnedObjectFromApi(response: List<Post>): Any {
                return "Returning a test string, should repsonse of a POST request"
            }

            override fun saveCallResult(response: List<Post>) {
                //do not care b/c no save data
            }

            override fun shouldLoadFromDb(): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<List<Post>>> {
                return apiService.getPosts()
            }

            override fun shouldFetch(data: Any?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Any> {
                return AbsentLiveData.create()
            }

            override fun setJob(job: Job) {
                addJob("getPostsDoNotSaveDb", job)
            }

        }.asLiveData()
    }
}