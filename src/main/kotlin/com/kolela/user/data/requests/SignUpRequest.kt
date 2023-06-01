package com.kolela.user.data.requests

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)
