package com.stu71557.store.auth.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: Int = 6,
    val name: String,
    val email: String,
    val password: String,
    val remember: Boolean)
