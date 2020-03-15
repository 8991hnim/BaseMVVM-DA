package m.tech.basemvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import m.tech.basemvvm.models.Post
import m.tech.basemvvm.repositories.PostRepository
import m.tech.basemvvm.util.DataState
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _listPosts: MediatorLiveData<DataState<List<Post>>> = MediatorLiveData()
    private val _postResponse: MediatorLiveData<DataState<Any>> = MediatorLiveData()

    val listPosts: LiveData<DataState<List<Post>>>
        get() = _listPosts

    val postResponse: LiveData<DataState<Any>>
        get() = _postResponse

    fun getPosts(){
        val repoSource = repository.getPosts()
        _listPosts.addSource(repoSource) {
            if(it is DataState.Success || it is DataState.Error)
                _listPosts.removeSource(repoSource)

            _listPosts.value = it
        }
    }

    fun getPostsNoSaveDb(){
        val repoSource = repository.getPostsDoNotSaveDb()
        _postResponse.addSource(repoSource) {
            if(it is DataState.Success || it is DataState.Error)
                _postResponse.removeSource(repoSource)

            _postResponse.value = it
        }
    }

    fun cancelActiveJobs() {
        repository.cancelActiveJobs()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}