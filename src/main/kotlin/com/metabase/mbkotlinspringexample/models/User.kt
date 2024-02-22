package com.metabase.mbkotlinspringexample.models

data class User(
    val name: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val accountId: String,
    val accountName: String,
    val password: String
)