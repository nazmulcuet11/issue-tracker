package com.issueTracker.repositories.interfaces

interface TokenRepository {
    suspend fun insert(token: String): Boolean
    suspend fun delete(token: String): Boolean
    suspend fun exists(token: String): Boolean
}
