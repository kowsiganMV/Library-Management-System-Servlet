
package com.example.UserServlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.example.DBActions.BookCRUD;
import com.example.Model.Book;
import com.example.Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Testing
//        User user = new User();
//        user.setUserid(1);
//        user.setName("Teacher");
//        user.setPassword("123");
//        user.setEmail("kowsiganmv@gmail.com");
//        user.setRole("admin");
//        //Session Creation
//        HttpSession session = req.getSession();
//        session.setAttribute("User", user);

        //Fetch Book Details
        List<Book> books = BookCRUD.SelectBook(con);

        req.removeAttribute("Message");
        req.setAttribute("Books",books);
        req.setAttribute("Page","home");
        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
        rd.forward(req,resp);
    }
}
        