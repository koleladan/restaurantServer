package com.kolela.user.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.kolela.user.data.AdmUser

class JwtService {
    private val  issuer = "restaurantServer"
    private val jwtSecret =System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    val  verifier:JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()


    fun generateToken(adminUser: AdmUser): String {
        return JWT.create()
            .withSubject("RestaurantAuthentication")
            .withIssuer(issuer)
            .withClaim("email", adminUser.email)
            .sign(algorithm)
    }
}