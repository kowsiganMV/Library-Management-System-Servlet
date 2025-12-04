package com.example.DBActions;

import com.example.Main;
import com.example.Model.Book;
import com.example.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class BookCRUD {
    private static final Logger logger = Logger.getLogger(BookCRUD.class.getName());

    //Book Management
    public static List<Book> SelectBook(Connection con) {

        String query = "SELECT * FROM bookschema.booktable ORDER BY bookid";
        List<Book> books = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("bookid");
                String name = rs.getString("bookname");
                String author = rs.getString("author");
                String category = rs.getString("category");
                int count = rs.getInt("noofbooks");

                Book book = new Book(id, name, author, category, count);
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("-----> SELECT BOOK SQL EXCEPTION : " + e);
        }

        return books;
    }

    public static List<String[]> RentedBook(Connection con, int userId){
        List<String[]> books = new ArrayList<>();
        String query = "SELECT b.bookid, b.bookname, b.author,b.category, r.noofbooks FROM bookschema.booktable b JOIN bookschema.renttable r ON b.bookid = r.bookid and r.userid = ? ORDER BY b.bookid;";

        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String bookid = rs.getString("bookid");
                String bookname = rs.getString("bookname");
                String author = rs.getString("author");
                String category = rs.getString("category");
                String noofbooks = rs.getString("noofbooks");
                books.add(new String[]{bookid, bookname,author,category ,String.valueOf(noofbooks)});
            }
        }catch (SQLException e){
            System.out.println("------> RENTED BOOKS SQL EXCEPTION.");
        }
        logger.info("User : "+userId+" Get the Rented Books");
        return books;
    }

    public static List<String[]> RentedBookReport(Connection con){
        List<String[]> books = new ArrayList<>();
        String query = "SELECT u.userid, u.username, b.bookid, b.bookname, b.author, b.category, r.noofbooks FROM bookschema.booktable b JOIN bookschema.renttable r ON b.bookid = r.bookid JOIN userschema.usertable u ON r.userid = u.userid ORDER BY u.userid;";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String userid = rs.getString("userid");
                String username = rs.getString("username");
                String bookid = rs.getString("bookid");
                String bookname = rs.getString("bookname");
                String author = rs.getString("author");
                String category = rs.getString("category");
                String noofbooks = rs.getString("noofbooks");
                books.add(new String[]{userid, username, bookid, bookname,author,category ,noofbooks});
            }
        }catch (SQLException e){
            System.out.println("------> RENTED BOOKS SQL EXCEPTION.");
        }
        logger.info("Admin "+" Get the Rented Books Report");
        return books;
    }

    public static int numberofBooks(Connection con, int userId) {
        String query = "SELECT SUM(r.noofbooks) AS sum " +
                "FROM bookschema.renttable r " +
                "WHERE r.userid = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("sum"); // returns 0 if NULL
            }

        } catch (SQLException e) {
            System.out.println("----> Profile SQL EXCEPTION: " + e.getMessage());
        }

        return 0;
    }

    public static Book addBook(Connection con, Book book){
        String query = "INSERT INTO bookschema.booktable (bookname, author, category, noofbooks) VALUES (?, ?, ?, ?);";
        try(PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,book.getBookName());
            ps.setString(2,book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4,book.getNoOfBooks());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        book.setBookId(rs.getInt(1));
                    }
                }
                return book;
            }
        }catch (SQLException e){
            System.out.println("-----> ADD BOOK SQL EXCEPTION : "+e.toString());
        }
        return null;
    }

    public static boolean returnBook(Connection con, int bookId, int userId) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        try {
            con.setAutoCommit(false);

            String updateBook =
                    "UPDATE bookschema.booktable " +
                            "SET noofbooks = noofbooks + 1 " +
                            "WHERE bookid = ?";

            ps1 = con.prepareStatement(updateBook);
            ps1.setInt(1, bookId);

            int bookUpdated = ps1.executeUpdate();
            if (bookUpdated == 0) {
                con.rollback();
                return false;
            }

            String updateRent =
                    "UPDATE bookschema.renttable " +
                            "SET noofbooks = noofbooks - 1 " +
                            "WHERE bookid = ? AND userid = ?";

            ps2 = con.prepareStatement(updateRent);
            ps2.setInt(1, bookId);
            ps2.setInt(2, userId);

            int rentUpdated = ps2.executeUpdate();
            if (rentUpdated == 0) {
                con.rollback();
                return false;
            }

            String deleteZero =
                    "DELETE FROM bookschema.renttable " +
                            "WHERE bookid = ? AND userid = ? AND noofbooks <= 0";

            ps3 = con.prepareStatement(deleteZero);
            ps3.setInt(1, bookId);
            ps3.setInt(2, userId);
            ps3.executeUpdate();

            con.commit();
            logger.info("BOOK ( "+ bookId+" ) RETURNED BY "+userId);
            return true;

        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ignored) {}
            System.out.println("-----> RETURN BOOK SQL EXCEPTION : " + e);
            return false;
        } finally {
            try { if (ps1 != null) ps1.close(); } catch (SQLException ignored) {}
            try { if (ps2 != null) ps2.close(); } catch (SQLException ignored) {}
            try { if (ps3 != null) ps3.close(); } catch (SQLException ignored) {}
            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }





    public static boolean rentBook(Connection con, int bookId, int userId) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            con.setAutoCommit(false);

            String updateBook = "UPDATE bookschema.booktable SET noofbooks = noofbooks - 1 " +
                    "WHERE bookid = ? AND noofbooks > 0";

            ps1 = con.prepareStatement(updateBook);
            ps1.setInt(1, bookId);

            int bookUpdated = ps1.executeUpdate();

            if (bookUpdated == 0) {
                con.rollback();
                return false;
            }

            String rentQuery =
                    "INSERT INTO bookschema.renttable(userid, bookid, noofbooks) " +
                            "VALUES (?, ?, 1) " +
                            "ON CONFLICT(userid, bookid) " +      // If already exists
                            "DO UPDATE SET noofbooks = renttable.noofbooks + 1";

            ps2 = con.prepareStatement(rentQuery);
            ps2.setInt(1, userId);
            ps2.setInt(2, bookId);

            int rentUpdated = ps2.executeUpdate();

            if (rentUpdated == 0) {
                con.rollback();
                return false;
            }

            con.commit();
            logger.info("BOOK ( "+ bookId+" ) Rented BY "+userId);
            return true;

        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ignored) {}
            System.out.println("-----> RENT BOOK SQL EXCEPTION : " + e);
            return false;
        } finally {
            try { if (ps1 != null) ps1.close(); } catch (SQLException ignored) {}
            try { if (ps2 != null) ps2.close(); } catch (SQLException ignored) {}
            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }



}
