package com.metabase.mbkotlinspringexample.util

import com.metabase.mbkotlinspringexample.models.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {
    fun signUserToken(user: User, jwtSecret: String): String {
        val expirationSeconds = 60 * 0.25
        val issuedAt = Date()
        val expirationDate = Date(System.currentTimeMillis() + (expirationSeconds * 1000).toLong())

        return Jwts.builder()
            .subject(user.email)
            .claim("email", user.email)
            .claim("first_name", user.firstName)
            .claim("last_name", user.lastName)
            .claim("account_id", user.accountId)
            .claim("groups", listOf(user.accountName))
            .issuedAt(issuedAt)
            .expiration(expirationDate)
            .signWith(
                Keys.hmacShaKeyFor(jwtSecret.toByteArray()),
                Jwts.SIG.HS256
            ).compact()
    }
}
