package com.metabase.mbkotlinspringexample.models

data class LoginRequest(
    val email: String,
    val password: String
)