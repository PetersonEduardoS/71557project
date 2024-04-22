package com.stu71557.store.auth.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.stu71557.store.auth.domain.model.User

@Dao
interface UserDao {

    @Upsert
    suspend fun upsert(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUser(id: Int = 6) : User?
}