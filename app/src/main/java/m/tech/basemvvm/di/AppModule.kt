package m.tech.basemvvm.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import m.tech.basemvvm.R
import m.tech.basemvvm.SessionManager
import m.tech.basemvvm.api.ApiService
import m.tech.basemvvm.persistence.AppDatabase
import m.tech.basemvvm.persistence.AppDatabase.Companion.DATABASE_NAME
import m.tech.basemvvm.persistence.PostDao
import m.tech.basemvvm.util.Constants.BASE_URL
import m.tech.basemvvm.util.LiveDataCallAdapterFactory
import m.tech.basemvvm.util.PreferenceKeys
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Module
    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return application.getSharedPreferences(
                PreferenceKeys.APP_PREFERENCES,
                Context.MODE_PRIVATE
            )
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideSharedPrefsEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
            return sharedPreferences.edit()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideRetrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
        }

        //TODO: change this --- (optional: provide api services in different modules)
        @Singleton
        @Provides
        @JvmStatic
        fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
            return retrofitBuilder.build().create(ApiService::class.java)
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideAppDatabase(app: Application): AppDatabase {
            return Room
                .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        //TODO:(optional provide DAO here or in different modules)
        @Singleton
        @Provides
        @JvmStatic
        fun providePostDao(db: AppDatabase): PostDao {
            return db.getPostDao()
        }

        @Singleton
        @Provides
        fun provideRequestOptions(): RequestOptions {
            return RequestOptions
                .placeholderOf(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideRequestManager(
            application: Application,
            requestOptions: RequestOptions
        ): RequestManager {
            return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
        }


        @Singleton
        @Provides
        @JvmStatic
        fun provideSessionManager(application: Application): SessionManager {
            return SessionManager(application)
        }
    }


}