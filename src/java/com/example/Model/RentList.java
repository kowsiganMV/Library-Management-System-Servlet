package com.example.Model;

public class RentList {
    protected String userName;
    protected String bookName;
    protected int userId;
    protected int bookId;
    protected int noofbooks;

    public RentList(String userName, String bookName, int userId, int bookId, int noofbooks) {
        this.userName = userName;
        this.bookName = bookName;
        this.userId = userId;
        this.bookId = bookId;
        this.noofbooks = noofbooks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getNoofbooks() {
        return noofbooks;
    }

    public void setNoofbooks(int noofbooks) {
        this.noofbooks = noofbooks;
    }
}
