package org.example.commands;

import org.example.repositories.IUserRepository;
import org.example.entities.User;

import java.util.Scanner;

/***
 * Command design pattern.
 * Is executed when the ADD_USER command is chosen
 */
public class AddUserCommand implements ICommand {
    private IUserRepository userRepository;
    private Scanner scanner;

    private int userId = 0;

    public AddUserCommand(IUserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /***
     * read from command line the parameters for add a new user to database
     * @return true if no errors have been thrown
     */
    @Override
    public boolean execute() {
        System.out.println("Enter the user name:");
        String userName = scanner.nextLine();

        System.out.println("Enter the user age:");
        int userAge = scanner.nextInt();
        scanner.nextLine();
        while(userAge < 1 || userAge > 100){
            System.out.println("Enter the user age between 1 - 100:");
            userAge = scanner.nextInt();
            scanner.nextLine();
        }

        User user = new User(userId, userName, userAge, -1);
        userId++;
        userRepository.insertEntity(user);
        return true;
    }
}
