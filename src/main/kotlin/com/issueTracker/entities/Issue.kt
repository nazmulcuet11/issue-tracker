package com.issueTracker.entities

import java.util.Date

class Issue(
    id: Int,
    val title: String,
    val description: String?,
    val createdAt: Date,
): Entity(id)