package org.example

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class BookDAO {
    fun insert(book: Book): Int {
        val sql = "INSERT INTO books (id, title, author, year, publisher) VALUES (?, ?, ?, ?, ?)"
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null

        return try {
            connection = Database.getConnection()
            preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, book.id)
            preparedStatement.setString(2, book.title)
            preparedStatement.setString(3, book.author)
            preparedStatement.setInt(4, book.year)
            preparedStatement.setString(5, book.publisher)
            preparedStatement.executeUpdate()
            book.id
        } catch (e: SQLException) {
            e.printStackTrace()
            -1
        } finally {
            try {
                preparedStatement?.close()
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun update(book: Book): Boolean {
        val sql = "UPDATE books SET title = ?, author = ?, year = ?, publisher = ? WHERE id = ?"
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null

        return try {
            connection = Database.getConnection()
            preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, book.title)
            preparedStatement.setString(2, book.author)
            preparedStatement.setInt(3, book.year)
            preparedStatement.setString(4, book.publisher)
            preparedStatement.setInt(5, book.id)
            preparedStatement.executeUpdate() > 0
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            try {
                preparedStatement?.close()
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun deleteById(id: Int): Boolean {
        val sql = "DELETE FROM books WHERE id = ?"
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null

        return try {
            connection = Database.getConnection()
            preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            preparedStatement.executeUpdate() > 0
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            try {
                preparedStatement?.close()
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun selectById(id: Int): Book? {
        val sql = "SELECT * FROM books WHERE id = ?"
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        return try {
            connection = Database.getConnection()
            preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                Book(
                    id = resultSet.getInt("id"),
                    title = resultSet.getString("title"),
                    author = resultSet.getString("author"),
                    year = resultSet.getInt("year"),
                    publisher = resultSet.getString("publisher")
                )
            } else {
                null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        } finally {
            try {
                resultSet?.close()
                preparedStatement?.close()
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    fun selectAll(): List<Book> {
        val sql = "SELECT * FROM books"
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null
        val books = mutableListOf<Book>()

        return try {
            connection = Database.getConnection()
            preparedStatement = connection.prepareStatement(sql)
            resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val book = Book(
                    id = resultSet.getInt("id"),
                    title = resultSet.getString("title"),
                    author = resultSet.getString("author"),
                    year = resultSet.getInt("year"),
                    publisher = resultSet.getString("publisher")
                )
                books.add(book)
            }
            books
        } catch (e: SQLException) {
            e.printStackTrace()
            emptyList()
        } finally {
            try {
                resultSet?.close()
                preparedStatement?.close()
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}