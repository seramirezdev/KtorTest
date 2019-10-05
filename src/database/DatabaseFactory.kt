package com.seramirezdev.database

import com.seramirezdev.dto.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
    fun init() {
        Database.connect(getConfigHikari())
        createTables()
    }

    private fun createTables() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Users, Comments, Places, Images, Favorites)

            if (Users.selectAll().count() == 0) {
                Users.insert {
                    it[name] = "Sergio"
                    it[username] = "seramirezdev"
                    it[password] = "0000"
                    it[role] = "admin"
                    it[fcmToken] = ""
                }
            }
        }
    }

    private fun getConfigHikari(): HikariDataSource {
        val config = HikariConfig()
        config.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        config.jdbcUrl = "jdbc:/postgres://localhost:5432/seminario"
        config.username = "seminario"
        config.password = "admin"
        config.maximumPoolSize = 3
        config.validate()

        return HikariDataSource(config)
    }
}