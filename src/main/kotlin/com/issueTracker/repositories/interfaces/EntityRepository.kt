package com.issueTracker.repositories.interfaces

interface EntityRepository<TEntity> {
    suspend fun selectAll(): List<TEntity>
    suspend fun selectById(id: Int): TEntity?
    suspend fun insert(entity: TEntity): TEntity?
    suspend fun update(entity: TEntity): Boolean
    suspend fun delete(entity: TEntity): Boolean
    suspend fun deleteById(id: Int): Boolean
}