package com.stu71557.store.di

import android.content.Context
import androidx.room.Room
import com.stu71557.store.auth.data.local.UserDao
import com.stu71557.store.auth.data.remote.AuthApiService
import com.stu71557.store.auth.data.repository.AuthRepositoryImpl
import com.stu71557.store.auth.domain.repository.AuthRepository
import com.stu71557.store.cart.data.local.datasource.AppDatabase
import com.stu71557.store.cart.data.local.datasource.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthApiService(): AuthApiService {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database"
        ).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideAuthRepository(userDao: UserDao): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}