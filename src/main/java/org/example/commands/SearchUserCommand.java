package org.example.commands;

import org.example.repositories.IUserRepository;
import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the SEARCH_USER command is chosen
 */
public class SearchUserCommand implements ICommand {
    private IUserRepository userRepository;
    private Scanner scanner;

    public SearchUserCommand(IUserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /***
     * read from command line a keyword for searching after in users database
     * @return true if no errors have been thrown
     */
    @Override
    public boolean execute() {
        System.out.println("Enter a keyword to search for users:");
        String keyword = scanner.nextLine();

        userRepository.getUserByName(keyword);
        return true;
    }
}
