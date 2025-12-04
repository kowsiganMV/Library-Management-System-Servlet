package com.example.UserServlets;

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
import java.util.List;

@WebServlet("/returnbook")
public class ReturnServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("User");

        int bookid = Integer.parseInt(req.getParameter("bookid"));

        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");

        req.setAttribute("Page","rentedbooks");
        if(!BookCRUD.returnBook(con,bookid,u.getUserid())){
            req.setAttribute("Message","Something Went Wrong While Renting Your Book !");
            rd.forward(req,res);
            return;
        }
        req.setAttribute("SuccessMessage","Book SuccessFully Returned. ");
        List<String[]> books = BookCRUD.RentedBook(con,u.getUserid());
        req.setAttribute("bookList",books);
        rd.forward(req,res);
    }
}
