package com.issueTracker.repositories.interfaces

interface IEntityRepository<TEntity> {
    suspend fun getAll(): List<TEntity>
}