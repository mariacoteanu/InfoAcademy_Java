package org.example.commands;

import org.example.repositories.IBookRepository;
import org.example.repositories.IUserRepository;
import org.example.enums.Status;

import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the BORROW_BOOK command is chosen
 */
public class BorrowBookCommand implements ICommand {
    private IBookRepository bookRepository;
    private IUserRepository userRepository;
    private Scanner scanner;

    public BorrowBookCommand(IBookRepository bookRepository, IUserRepository userRepository, Scanner scanner) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /***
     * read from command line the user ID and the book ID for borrowing a book
     * @return true if no errors have been thrown
     */
    public boolean execute() {
        System.out.println("Enter the book ID:");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the borrower's ID:");
        int borrowerId = scanner.nextInt();
        scanner.nextLine();

        bookRepository.borrowBook(userRepository, Status.UNAVAILABLE.name(), bookId, borrowerId);
        return true;
    }
}
