package com.metabase.mbkotlinspringexample.util

import com.metabase.mbkotlinspringexample.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthUtil(
    @Autowired private val encryptPassword: BCryptPasswordEncoder,
) {

    private val users = listOf(
        User("admin", "admin@example.com", "Admin", "McGee", "1", "Admin", encryptPassword.encode("admin")),
        User("user", "user@example.com", "User", "McGoo", "2", "User", encryptPassword.encode("user")),
        User("Rene from Java", "rene@example.com", "ReneJava", "McGoo", "3", "User", encryptPassword.encode("foobar")),
    )

    fun authenticate(email: String, plainPassword: String): User? {
        val user = users.find { it.email == email }
        return if (user != null && encryptPassword.matches(plainPassword, user.password)) {
            user
        } else null
    }
}