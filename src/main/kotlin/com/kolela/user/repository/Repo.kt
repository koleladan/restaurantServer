package com.kolela.user.repository

import com.kolela.connection.DatabaseFactory.dbQuery
import com.kolela.user.data.AdmUser
import com.kolela.user.tables.AdmUserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class Repo {
    suspend fun SignUpUser(admUser: AdmUser) {
        dbQuery{
            AdmUserTable.insert {ut ->
                ut[AdmUserTable.firstName] = admUser.firstName
                ut[AdmUserTable.lastName] = admUser.lastName
                ut[AdmUserTable.id] = admUser.id
                ut[AdmUserTable.email] = admUser.email
                ut[AdmUserTable.password] = admUser.password
                ut[AdmUserTable.phoneNumber] = admUser.phoneNumber
            }

        }
    }

    suspend fun findUserByEmail(email: String) = dbQuery {
        AdmUserTable.select { AdmUserTable.email.eq(email) }
            .map { rowToAdmUser(it) }
            .singleOrNull()

    }

    private fun rowToAdmUser(row: ResultRow?): AdmUser? {
        if (row == null) {
            return null
        }
        return AdmUser(
            firstName = row[AdmUserTable.firstName],
            lastName = row[AdmUserTable.lastName],
            id = row[AdmUserTable.id],
            email = row[AdmUserTable.email],
            password = row[AdmUserTable.password],
            phoneNumber = row[AdmUserTable.phoneNumber]

        )
    }
}