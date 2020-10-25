package com.thuamiel.mvvmwithhilt.di

import com.thuamiel.mvvmwithhilt.respositry.MainRepository
import com.thuamiel.mvvmwithhilt.retrofit.BlogRetrofit
import com.thuamiel.mvvmwithhilt.retrofit.NetworkMapper
import com.thuamiel.mvvmwithhilt.room.BlogDao
import com.thuamiel.mvvmwithhilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, blogRetrofit, cacheMapper, networkMapper)
    }
}