package org.example.commands;

import org.example.entities.Book;
import org.example.enums.Genre;
import org.example.repositories.IBookRepository;

import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the ADD_BOOK command is chosen
 */
public class AddBookCommand implements ICommand {
    private IBookRepository bookRepository;
    private Scanner scanner;
    private int bookId = 0;

    public AddBookCommand(IBookRepository bookRepository, Scanner scanner) {
        this.bookRepository = bookRepository;
        this.scanner = scanner;
    }

    /***
     * read from command line the parameters for add a new book to database
     * @return true if no errors have been thrown
     */
    @Override
    public boolean execute() {
        System.out.println("Enter the book title: ");
        String title = scanner.nextLine();

        System.out.println("Enter the book author: ");
        String author = scanner.nextLine();

        boolean end = false;
        Book book = null;
        int choice;
        while(!end){
            System.out.println("Enter the book genre (1 - Fiction; 2 - Poetry; 3 - Sci-Fi):");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1: {
                    book = new Book(bookId, title, author, Genre.FICTION);
                    bookId++;
                    end = true;
                    break;
                }
                case 2: {
                    book = new Book(bookId, title, author, Genre.POETRY);
                    bookId++;
                    end = true;
                    break;
                }
                case 3: {
                    book = new Book(bookId, title, author, Genre.SCIFI);
                    bookId++;
                    end = true;
                    break;
                }
                default:{
                    System.out.println("Invalid genre.");
                }
            }
        }

        bookRepository.insertEntity(book);
        return true;
    }
}
