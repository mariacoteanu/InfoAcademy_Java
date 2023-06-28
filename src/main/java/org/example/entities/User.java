package org.example.entities;

public class User {
    private int id;
    private String name;
    private int age;
    private int bookId;

    public User(int id, String name, int age, int bookId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bookId=" + bookId +
                '}';
    }
}
