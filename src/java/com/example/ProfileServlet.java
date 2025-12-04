
package com.example.AdminServlets;

import com.example.DBActions.BookCRUD;
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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("User");

        int noOfBooks = BookCRUD.numberofBooks(con,u.getUserid());

        req.setAttribute("RentedCount",noOfBooks);
        req.setAttribute("Page","profile");
        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
        rd.forward(req,resp);
    }


}
        