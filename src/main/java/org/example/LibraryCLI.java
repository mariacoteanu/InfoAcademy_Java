package org.example;

import org.example.repositories.BookRepository;
import org.example.commands.*;
import org.example.repositories.IBookRepository;
import org.example.repositories.IUserRepository;
import org.example.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/***
 * Client class - where the app starts
 */
public class LibraryCLI {
    private Map<Integer, ICommand> commands;
    private Scanner scanner;

    public LibraryCLI(IBookRepository bookRepository, IUserRepository userRepository) {
        scanner = new Scanner(System.in);
        commands = new HashMap<>();
        commands.put(1, new AddBookCommand(bookRepository, scanner));
        commands.put(2, new AddUserCommand(userRepository, scanner));
        commands.put(3, new BorrowBookCommand(bookRepository, userRepository, scanner));
        commands.put(4, new ReturnBookCommand(bookRepository, userRepository, scanner));
        commands.put(5, new SearchBookCommand(bookRepository, scanner));
        commands.put(6, new SearchUserCommand(userRepository, scanner));
        commands.put(7, new SearchBorrowersCommand(userRepository, scanner));
    }

    /***
     * the method that run the command line
     */
    public void start(){
        int choice;
        String terminalCommands =  "\nTHE USEFUL COMMANDS ARE:"
                + "\n1. Add new book"
                + "\n2. Add new user to borrow books"
                + "\n3. An user want to borrow a book"
                + "\n4. An user return the borrowed book"
                + "\n5. Search book"
                + "\n6. Search user"
                + "\n7. Search all borrowers"
                + "\n8. Close the program";
        System.out.println("WELCOME TO LIBRARY TERMINAL!");

        while(true) {
            System.out.println(terminalCommands);
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 8){
                System.out.println("Thank you for using or Library!");
                return;
            }

            ICommand command = commands.get(choice);
            if (command != null) {
                executeCommand(command);
            }else{
                System.out.println("Invalid choice. Please enter valid command!");
            }
        }
    }

    /***
     * method to execute the command - from Command design pattern
     * @param command - the specific command that is executed
     */
    private static void executeCommand(ICommand command){
        if (command.execute()){
            System.out.println("Command executed successfully! \n");
        }
    }

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        IUserRepository iUserRepository = new UserRepository(bookRepository.getConnection());
        LibraryCLI libraryCLI = new LibraryCLI(bookRepository, iUserRepository);

        libraryCLI.start();
    }
}