package org.example.repositories;

import org.example.entities.Book;

/***
 * Repository design pattern - interface for Book entity with specific methods
 */
public interface IBookRepository extends IRepository<Book> {
    public void borrowBook(IUserRepository iUserRepository, String status, int bookID, int userID);
    public void returnBook(IUserRepository iUserRepository, String status, int bookID, int userID);
    public void searchBooks(String keyword);
}
