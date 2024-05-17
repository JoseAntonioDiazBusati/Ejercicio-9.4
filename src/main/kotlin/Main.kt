package org.example

import java.sql.Connection
import java.sql.SQLException

fun main() {
    // Crear la tabla de libros (esto debería hacerse una sola vez, no en cada ejecución)
    createBookTable()

    val bookDAO = BookDAO()

    // Insertar un nuevo libro
    val newBook = Book(1, "Arsène Lupin Caballero Ladron", "Maurice Leblanc", 1907, "Clasicos Ilustrados")
    val newBookId = bookDAO.insert(newBook)
    println("Inserted Book ID: $newBookId")

    // Actualizar un libro
    val updatedBook = newBook.copy(title = "Effective Java (2nd Edition)")
    val isUpdated = bookDAO.update(updatedBook)
    println("Book updated: $isUpdated")

    // Seleccionar un libro por ID
    val selectedBook = bookDAO.selectById(1)
    println("Selected Book: $selectedBook")

    // Seleccionar todos los libros
    val allBooks = bookDAO.selectAll()
    println("All Books: $allBooks")

    // Eliminar un libro por ID
    val isDeleted = bookDAO.deleteById(1)
    println("Book deleted: $isDeleted")
}

fun createBookTable() {
    val sql = """
        CREATE TABLE IF NOT EXISTS books (
            id INT PRIMARY KEY,
            title VARCHAR,
            author VARCHAR,
            year INT,
            publisher VARCHAR
        )
    """
    var connection: Connection? = null
    var statement: java.sql.Statement? = null

    try {
        connection = Database.getConnection()
        statement = connection.createStatement()
        statement.execute(sql)
    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        try {
            statement?.close()
            connection?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}