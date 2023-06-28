package org.example.repositories;

import org.example.entities.User;

/***
 * Repository design pattern - interface for User entity with specific method
 */
public interface IUserRepository extends IRepository<User> {
    void getUserByName(String userName);
}
