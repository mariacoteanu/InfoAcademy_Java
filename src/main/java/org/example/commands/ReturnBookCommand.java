package org.example.commands;

import org.example.repositories.IBookRepository;
import org.example.repositories.IUserRepository;
import org.example.enums.Status;

import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the RETURN_BOOK command is chosen
 */
public class ReturnBookCommand implements ICommand {
    private IBookRepository bookRepository;
    private IUserRepository userRepository;
    private Scanner scanner;

    public ReturnBookCommand(IBookRepository bookRepository, IUserRepository userRepository, Scanner scanner) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /***
     * read from command line the user ID and the book ID for returning a book
     * @return true if no errors have been thrown
     */
    public boolean execute() {
        System.out.println("Enter the book ID:");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the borrower's ID:");
        int borrowerId = scanner.nextInt();
        scanner.nextLine();

        bookRepository.returnBook(userRepository, Status.AVAILABLE.name(), bookId, borrowerId);
        return true;
    }
}
