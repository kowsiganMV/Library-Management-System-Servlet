
package com.example.AdminServlets;

import com.example.DBActions.BookCRUD;
import com.example.Main;
import com.example.Model.Book;
import com.example.Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Logger;


@WebServlet("/addbook")
public class AddBookServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("Page","addbook");
        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
        rd.forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
        String bookname = (String)req.getParameter("bookname");
        String author = (String)req.getParameter("author");
        String category = (String)req.getParameter("category");
        String noOfBooksStr = req.getParameter("noofbooks");

        req.setAttribute("Page", "addbook");

        int noOfBooks = 0;

        try {
            noOfBooks = Integer.parseInt(noOfBooksStr);
            if (noOfBooks <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            req.setAttribute("Message", "Please enter a valid number of books.");
            RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
            rd.forward(req, res);
            return;
        }

        Book book = new Book(bookname,author,category,noOfBooks);
        book = BookCRUD.addBook(con, book);

        if(book==null){
            RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
            req.setAttribute("Message","Server Error While Book Adding.");
            rd.forward(req,res);
        }
        req.removeAttribute("Message");

        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
        String msg = bookname + " Successfully Added!";
        req.setAttribute("BookAdded",msg);

        logger.info("NEW BOOK ADDED BY ADMIN : "+((User)req.getSession().getAttribute("User")).getName());
        rd.forward(req,res);
    }
}
        