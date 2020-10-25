package com.thuamiel.mvvmwithhilt.respositry

import com.thuamiel.mvvmwithhilt.model.Blog
import com.thuamiel.mvvmwithhilt.retrofit.BlogRetrofit
import com.thuamiel.mvvmwithhilt.retrofit.NetworkMapper
import com.thuamiel.mvvmwithhilt.room.BlogDao
import com.thuamiel.mvvmwithhilt.room.CacheMapper
import com.thuamiel.mvvmwithhilt.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlog = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlog)
            blogs.forEach {
                blogDao.insert(cacheMapper.mapToEntity(it))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}