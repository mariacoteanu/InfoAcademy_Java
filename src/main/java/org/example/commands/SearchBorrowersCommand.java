package org.example.commands;

import org.example.entities.User;
import org.example.repositories.IUserRepository;
import java.util.List;
import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the SEARCH_BORROWERS command is chosen
 */
public class SearchBorrowersCommand implements ICommand {
    private IUserRepository userRepository;
    private Scanner scanner;

    public SearchBorrowersCommand(IUserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /***
     * read all users from users database and filter only the ones who have borrowed a book
     * @return true if no errors have been thrown
     */
    @Override
    public boolean execute() {
        List<User> allUsers = userRepository.getAll();
        allUsers.stream()
                .filter(user-> user.getBookId()>0)
                .forEach(System.out::println);
        return true;
    }
}
