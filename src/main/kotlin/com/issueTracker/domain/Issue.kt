package com.issueTracker.domain

import java.util.Date

data class Issue(
    val title: String,
    val description: String,
    val createdAt: Date,
)