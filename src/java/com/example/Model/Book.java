package com.example.Model;

public class Book {
    private int bookId;
    private String bookName;
    private String Author;
    private String Category;
    private int noOfBooks;

    public Book(){

    }
    public Book(int bookId, String bookName, String author, String category, int noOfBooks) {
        this.bookId = bookId;
        this.bookName = bookName;
        Author = author;
        Category = category;
        this.noOfBooks = noOfBooks;
    }
    public Book(String bookName, String author, String category, int noOfBooks) {
        this.bookName = bookName;
        Author = author;
        Category = category;
        this.noOfBooks = noOfBooks;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getNoOfBooks() {
        return noOfBooks;
    }

    public void setNoOfBooks(int noOfBooks) {
        this.noOfBooks = noOfBooks;
    }
}
