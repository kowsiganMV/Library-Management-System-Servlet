
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@WebServlet("/rentreport")
public class RentReportServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String[]> books = BookCRUD.RentedBookReport(con);

        req.setAttribute("bookList",books);
        req.setAttribute("Page","rentreport");
        RequestDispatcher rd = req.getRequestDispatcher("assets/jsp/Frame.jsp");
        rd.forward(req,resp);
    }
}
        