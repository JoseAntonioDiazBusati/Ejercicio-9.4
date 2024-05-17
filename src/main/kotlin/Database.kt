package org.example

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

object Database {
    private const val URL = "jdbc:h2:./H2"
    private const val USER = "user"
    private const val PASSWORD = "user"

    init {
        try {
            Class.forName("org.h2.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLException) {
            throw RuntimeException("Error connecting to the database", e)
        }
    }
}