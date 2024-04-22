package com.stu71557.store.cart.data.local.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.stu71557.store.cart.domain.model.Cart

@Dao
interface CartDao {

    @Upsert
    suspend fun upsert(cart: Cart)

    @Query("SELECT * FROM Cart WHERE id = :id")
    suspend fun getCartById(id: Int = 3): Cart?

    @Delete
    suspend fun delete(cart: Cart)
}