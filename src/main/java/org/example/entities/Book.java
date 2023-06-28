package org.example.entities;

import org.example.enums.Genre;
import org.example.enums.Status;

public class Book {
    private int id;
    private String title;
    private String author;
    private Status status;
    private int borrowedBy;

    private Genre genre;

    public Book(int id, String title, String author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = Status.AVAILABLE;
        this.borrowedBy = 0;
        this.genre = genre;
    }

    public Book(int id, String title, String author, int borrowedBy) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowedBy = borrowedBy;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status.name();
    }


    public int getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public Status isBorrowed(){
        return status;
    }

    public String getGenre() {
        return genre.name();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", borrowedBy='" + borrowedBy + '\'' +
                '}';
    }
}
