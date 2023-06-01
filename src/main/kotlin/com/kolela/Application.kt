package com.kolela

import com.kolela.connection.DatabaseFactory
import io.ktor.server.application.*
import com.kolela.plugins.*
import com.kolela.user.authentication.JwtService
import com.kolela.user.authentication.hash
import com.kolela.user.data.AdmUser
import com.kolela.user.repository.Repo
import com.kolela.user.routes.AdmUserRoutes
import io.ktor.serialization.gson.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(testing: Boolean = false) {


    DatabaseFactory.init()
    val db = Repo()
    val jwtService = JwtService()
    val hashFunction = {s: String -> hash(s) }

    install(Authentication) {

    }
    install(ContentNegotiation) {
        gson {

        }
    }
    routing {
//        get("/token"){
//            val email = call.request.queryParameters["email"]!!
//            val password = call.request.queryParameters["password"]!!
//            val firstName = call.request.queryParameters["firstName"]!!
//            val lastName = call.request.queryParameters["lastName"]!!
//            val id = call.request.queryParameters["id"]!!
//            val phoneNumber = call.request.queryParameters["phoneNumber"]!!
//
//            val adminUser = AdmUser(firstName,lastName,email,id,phoneNumber, hashFunction(password))
//            call.respond(jwtService.generateToken(adminUser))
//        }
        AdmUserRoutes(db, jwtService, hashFunction)
    }



  //  configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
