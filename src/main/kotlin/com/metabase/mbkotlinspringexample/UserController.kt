package com.metabase.mbkotlinspringexample

import com.metabase.mbkotlinspringexample.models.LoginRequest
import com.metabase.mbkotlinspringexample.models.User
import com.metabase.mbkotlinspringexample.util.AuthUtil
import com.metabase.mbkotlinspringexample.util.JwtUtil
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException


@RestController
class UserController(
    @Autowired private val restTemplate: RestTemplate,
    @Autowired private val jwtUtil: JwtUtil,
    @Autowired private val authUtil: AuthUtil,
    @Value("\${metabase.site.url}") private val siteUrl: String,
    @Value("\${client.site.url}") private val clientUrl: String,
    @Value("\${metabase.jwt.secretKey}") private val jwtSecret: String
) {

    @GetMapping("/")
    fun index(session: HttpSession): ResponseEntity<String> {
        val currentUser = session.getAttribute("currentUser") as? User
        return if (currentUser == null)
            ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("Location", clientUrl)
                .body("Redirecting to login")
        else ResponseEntity.ok("Hello, ${currentUser.firstName}")
    }


    @GetMapping("/sso/metabase")
    fun authenticateToMetabase(session: HttpSession): ResponseEntity<String> {
        val currentUser = session.getAttribute("currentUser") as? User
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in")

        val token = jwtUtil.signUserToken(currentUser, jwtSecret)
        val ssoUrl = "$siteUrl/auth/sso?jwt=$token&token=true"

        val response = restTemplate.getForEntity(ssoUrl, String::class.java)
        return ResponseEntity.ok(
            response.body ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Failed to authenticate with Metabase"
            )
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest, session: HttpSession): ResponseEntity<String> {
        val user = authUtil.authenticate(request.email, request.password)
        return if (user != null) {
            session.setAttribute("currentUser", user);
            ResponseEntity.ok("Success")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed")
        }
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<String> {
        session.invalidate()
        return ResponseEntity.ok("Logged out")
    }
}