package com.metabase.mbkotlinspringexample.models

data class MetabaseAuthToken(
    val token: String, val exp: String, val iat: String
)