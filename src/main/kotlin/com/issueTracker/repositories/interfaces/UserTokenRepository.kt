package com.issueTracker.repositories.interfaces

interface UserTokenRepository {
    suspend fun insert(token: String, userId: Int): Boolean
    suspend fun deleteAllToken(userId: Int): Boolean
    suspend fun delete(token: String, userId: Int): Boolean
    suspend fun exists(token: String, userId: Int): Boolean
}
