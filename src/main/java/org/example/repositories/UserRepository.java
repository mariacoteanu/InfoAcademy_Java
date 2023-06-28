package org.example.repositories;

import org.example.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * specialized class of IUserRepository interface for implementing CRUD methods
 */
public class UserRepository implements IUserRepository {

    private Connection connection;
    public UserRepository(Connection connection){
        this.connection = connection;
    }

    /***
     * insert a user to users database - like making a library pass access
     * @param user
     */
    @Override
    public void insertEntity(User user) {
        String sql = "INSERT INTO users (name, age) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * find a username in users database
     * @param userName
     */
    @Override
    public void getUserByName(String userName) {
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        boolean entered = false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                entered = true;
                int userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int bookBorrowed = resultSet.getInt("bookId");

                System.out.println("User ID: " + userId);
                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
                System.out.println("Book ID borrowed: " + bookBorrowed);
            }
            if(!entered){
                System.out.println("No user matched!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * find a user in database based on his ID
     * @param id - user ID
     * @return User Object
     */
    @Override
    public User getEntity(int id) {
        String sql = "SELECT * FROM users WHERE userId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int bookBorrowed = resultSet.getInt("bookId");

                return new User(userId, name, age, bookBorrowed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * show all users in database
     * @return ArrayList of Users
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int bookBorrowed = resultSet.getInt("bookId");

                users.add(new User(userId, name, age, bookBorrowed));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
