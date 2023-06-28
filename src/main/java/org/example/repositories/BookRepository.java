package org.example.repositories;

import org.example.entities.Book;
import org.example.entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * specialized class of IBookRepository interface for implementing CRUD methods
 */
public class BookRepository implements IBookRepository{

    private Connection connection;

    /***
     * BookRepository constructor:
     * is initiated the connection with mysql database
     */
    public BookRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * generated getter for connection object to be user only one connection with database in all project
     * @return database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /***
     * insert a book to books database
     * @param book
     */
    public void insertEntity(Book book) {
        String sql = "INSERT INTO books (title, author, genre, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * when a user want to borrow a book,
     * it is checked if the book is not borrowed or the user has borrowed another book
     * if is valid, the databases are updated according to book ID and borrower ID
     * @param status the status of book - UNAVAILABLE
     * @param bookId - book ID that is borrowed
     * @param borrowerId - user ID that want to borrow
     */
    public void borrowBook(IUserRepository iUserRepository, String status, int bookId, int borrowerId) {
        if(validateBorrow(iUserRepository, bookId, borrowerId)){
            String sql1 = "UPDATE books SET status = ?, borrower = ? WHERE idbooks = ?";
            String sql2 = "UPDATE users SET bookId = ? WHERE userId = ?";

            try (PreparedStatement statement1 = connection.prepareStatement(sql1)){
                statement1.setString(1, status);
                statement1.setInt(2, borrowerId);
                statement1.setInt(3, bookId);
                statement1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement statement2 = connection.prepareStatement(sql2)){
                statement2.setInt(1, bookId);
                statement2.setInt(2, borrowerId);
                statement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Book borrowed or user already has borrowed another book");
        }

    }

    /***
     * when a user want to return the borrowed book,
     * it is checked if the user really borrowed this book
     * if is valid - the book is erased from users database and the user is erased from book database
     * @param iUserRepository - one instance of user database used in project
     * @param status - the status after returning - AVAILABLE
     * @param bookId - returned book ID
     * @param userId - user's ID who return the book
     */
    public void returnBook(IUserRepository iUserRepository, String status, int bookId, int userId) {
        if(validateReturn(iUserRepository, bookId, userId)){
            String sql1 = "UPDATE books SET status = ?, borrower = null WHERE idbooks = ?";
            String sql2 = "UPDATE users SET bookId = null WHERE userId = ?";

            try (PreparedStatement statement1 = connection.prepareStatement(sql1)) {
                statement1.setString(1, status);
                statement1.setInt(2, bookId);
                statement1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement statement2 = connection.prepareStatement(sql2)) {
                statement2.setInt(1, userId);
                statement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("User not borrowed this book!");
        }

    }

    /***
     * searching through books database according to the keyword
     * @param keyword - to be searched in book database
     */
    public void searchBooks(String keyword) {
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
        boolean entered = false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                entered = true;
                int id = resultSet.getInt("idbooks");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                String borrowed = resultSet.getString("status");
                int borrower = resultSet.getInt("borrower");

                System.out.println("Book ID: " + id);
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Genre: " + genre);
                System.out.println("Borrowed: " + borrowed);
                System.out.println("Borrower ID: " + borrower);
                System.out.println();
            }
            if(!entered){
                System.out.println("No book matched!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * show all books in database
     * @return ArrayList of Books
     */
    public List<Book> getAll(){
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idbooks");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int borrower = resultSet.getInt("borrower");

                books.add(new Book(id, title, author, borrower));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /***
     * create a Book object from a db result
     * @param bookId - the book ID searched in books database
     * @return Book object with id + title + author + borrower ID
     */
    public Book getEntity(int bookId) {
        String sql = "SELECT * FROM books WHERE idbooks = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("idbooks");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int borrower = resultSet.getInt("borrower");

                return new Book(id, title, author, borrower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateBorrow(IUserRepository iUserRepository, int bookId, int borrowerId){
        User user = iUserRepository.getEntity(borrowerId);
        Book book = getEntity(bookId);

        return user.getBookId() == 0 && book.getBorrowedBy() == 0;
    }

    private boolean validateReturn(IUserRepository iUserRepository, int bookId, int borrowerId){
        User user = iUserRepository.getEntity(borrowerId);
        Book book = getEntity(bookId);

        return user.getBookId() == bookId && book.getBorrowedBy() == borrowerId;
    }
}
