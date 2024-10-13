package com.issueTracker.models

class User(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var passwordHash: String,
    var role: Role
)
