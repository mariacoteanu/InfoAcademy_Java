package org.example.repositories;

import java.util.List;

/***
 * Repository design pattern
 * @param <T> - entity - Book / User
 */
public interface IRepository<T> {
    public T getEntity(int id);
    public void insertEntity(T entity);
    public List<T> getAll();
}
