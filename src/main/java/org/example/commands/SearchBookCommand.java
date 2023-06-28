package org.example.commands;

import org.example.repositories.IBookRepository;
import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the SEARCH_BOOK command is chosen
 */
public class SearchBookCommand implements ICommand {
    private IBookRepository bookRepository;
    private Scanner scanner;

    public SearchBookCommand(IBookRepository bookRepository, Scanner scanner) {
        this.bookRepository = bookRepository;
        this.scanner = scanner;
    }

    /***
     * read from command line a keyword for searching after in book database
     * @return true if no errors have been thrown
     */
    public boolean execute() {
        System.out.println("Enter a keyword to search for books:");
        String keyword = scanner.nextLine();

        bookRepository.searchBooks(keyword);
        return true;
    }
}