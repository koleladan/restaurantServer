package com.kolela.user.tables

import org.jetbrains.exposed.sql.Table

object AdmUserTable: Table() {
    val id = varchar("id", 256)
    val firstName = varchar("first_name", 256)
    val lastName = varchar("last_name", 256)
    val phoneNumber = varchar("phone_number", 20)
    val email = varchar("email", 256)
    var password = varchar("password", 256)

    override val primaryKey = PrimaryKey(id)
}