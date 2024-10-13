package com.issueTracker.constants

const val REQUEST_SCOPE_NAME = "REQUEST_SCOPE"
const val MAXIMUM_DB_CONNECTION_PULL_SIZE = 3

object Permissions {
    object Users {
        const val VIEW_USERS = "view_users"
        const val EDIT_USERS = "edit_users"
    }
}

object Users {
    const val ADMIN_USER_ID = 1
}

object Roles {
    const val USER_ROLE_ID = 1
    const val ADMIN_ROLE_ID = 2
}