package com.example.UserServlets;

import com.example.DBActions.BookCRUD;
import com.example.DBActions.Encoder;
import com.example.DBActions.UserCRUD;
import com.example.Model.Book;
import com.example.Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/rentbook")
public class RentServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("User");

        int userId = user.getUserid();
        String bookIdStr = req.getParameter("bookid");
        int bookId = 0;

        List<Book> books = BookCRUD.SelectBook(con);

        req.removeAttribute("Message");
        req.setAttribute("Books",books);
        req.setAttribute("Page","home");

        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
        try {
            bookId = Integer.parseInt(bookIdStr);
            if (bookId <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            req.setAttribute("Message", "Invalid Book !");
            rd.forward(req, res);
            return;
        }

        if(!BookCRUD.rentBook(con,bookId,userId)){
            req.setAttribute("Message","Book is Not Available");
            rd.forward(req,res);
            return;
        }
        res.sendRedirect("home");
    }
}
