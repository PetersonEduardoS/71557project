package com.stu71557.store.cart.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stu71557.store.auth.data.local.UserDao
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.cart.domain.converters.ProductInfoConverter
import com.stu71557.store.cart.domain.model.Cart

@Database(entities = [Cart::class, User::class], version = 2)
@TypeConverters(ProductInfoConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun userDao(): UserDao
}