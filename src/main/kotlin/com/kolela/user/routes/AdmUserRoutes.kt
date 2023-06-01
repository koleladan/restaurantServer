package com.kolela.user.routes

import com.kolela.user.authentication.JwtService
import com.kolela.user.data.AdmUser
import com.kolela.user.data.requests.LoginRequest
import com.kolela.user.data.requests.SignUpRequest
import com.kolela.user.data.responses.LoginResponse
import com.kolela.user.data.responses.SignUpResponse
import com.kolela.user.repository.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val API_VERSION = "/v1"
const val ADMUSERS = "$API_VERSION/admUsers"
const val SIGNUP_REQUEST = "$ADMUSERS/signup"
const val LOGIN_REQUEST = "$ADMUSERS/login"

@Location(SIGNUP_REQUEST)
class AdmUserSignUpRoute

@Location(LOGIN_REQUEST)
class AdmUserLoginRoute



fun Route.AdmUserRoutes(
    db: Repo,
    jwtService: JwtService,
    hashFunction: (String) -> String

) {
    post<AdmUserSignUpRoute>("signup"){
        val signUpRequest =  try {

            call.receive<SignUpRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SignUpResponse(false, "Missing Some Fields"))
            return@post
        }
        try {
            val admUser = AdmUser(signUpRequest.firstName,signUpRequest.lastName, signUpRequest.email,signUpRequest.phoneNumber,hashFunction(signUpRequest.password), signUpRequest.phoneNumber)
            db.SignUpUser(admUser)
            call.respond(HttpStatusCode.OK, SignUpResponse(true, jwtService.generateToken(admUser)))

        } catch (e:Exception) {
            call.respond(HttpStatusCode.Conflict,SignUpResponse(false, e.message ?: "An error occurred"))

        }

    }

    post<AdmUserLoginRoute>("login") {
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, LoginResponse(false, "Missing some field"))
            return@post
        }
        try {
            val admUser = db.findUserByEmail(loginRequest.email)

            if (admUser == null) {
                call.respond(HttpStatusCode.BadRequest, LoginResponse(false, "Incorrect details"))
            }
            else {
                if (admUser.password == hashFunction(loginRequest.password)){
                    call.respond(HttpStatusCode.OK, LoginResponse(true, jwtService.generateToken(admUser)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, LoginResponse(false, "Incorrect details"))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, LoginResponse(false, e.message ?: "An error occurred"))
        }
    }

}