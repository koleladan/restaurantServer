package com.kolela.user.repository

import com.kolela.connection.DatabaseFactory.dbQuery
import com.kolela.user.data.AdminUser
import com.kolela.user.tables.AdmUserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class Repo {
    suspend fun SignUpUser(adminUser: AdminUser) {
        dbQuery{
            AdmUserTable.insert {ut ->
                ut[AdmUserTable.firstName] = adminUser.firstName
                ut[AdmUserTable.lastName] = adminUser.lastName
                ut[AdmUserTable.id] = adminUser.id
                ut[AdmUserTable.email] = adminUser.email
                ut[AdmUserTable.password] = adminUser.password
                ut[AdmUserTable.phoneNumber] = adminUser.phoneNumber
            }

        }
    }

    suspend fun findUserById(id: String) = dbQuery {
        AdmUserTable.select { AdmUserTable.id.eq(id) }
            .map { rowToAdmUser(it) }
            .singleOrNull()

    }

    private fun rowToAdmUser(row: ResultRow?): AdminUser? {
        if (row == null) {
            return null
        }
        return AdminUser(
            firstName = row[AdmUserTable.firstName],
            lastName = row[AdmUserTable.lastName],
            id = row[AdmUserTable.id],
            email = row[AdmUserTable.email],
            password = row[AdmUserTable.password],
            phoneNumber = row[AdmUserTable.phoneNumber]

        )
    }
}