package m.tech.basemvvm.di.main

import dagger.Module
import dagger.Provides
import m.tech.basemvvm.SessionManager
import m.tech.basemvvm.api.ApiService
import m.tech.basemvvm.persistence.PostDao
import m.tech.basemvvm.repositories.PostRepository
import retrofit2.Retrofit

@Module
class MainModule {
    @Module
    companion object {
        @MainScope
        @Provides
        fun provideMainRepository(
            sessionManager: SessionManager,
            postDao: PostDao,
            apiService: ApiService
        ): PostRepository {
            return PostRepository(sessionManager, postDao, apiService)
        }
    }

}