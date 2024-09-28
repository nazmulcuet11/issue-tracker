package com.issueTracker.entities

class User(
    id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val passwordHash: String,
    var role: Role? = null
): Entity(id)
