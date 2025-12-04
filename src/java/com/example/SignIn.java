package com.example;

import com.example.DBActions.Encoder;
import com.example.DBActions.UserCRUD;
import com.example.Model.User;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Logger;

public class SignIn extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/assets/jsp/SignIn.jsp");
        rd.forward(req,res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter out = res.getWriter();

        String name = req.getParameter("username");
        String password = req.getParameter("password");

        //Encode & Signin
        String passKey = Encoder.encode(password);
        User user = UserCRUD.Signin(con,name,passKey);

        if(user==null){
            RequestDispatcher rd = req.getRequestDispatcher("/assets/jsp/SignIn.jsp");
            req.setAttribute("Message","UserName/Password Wrong.");
            logger.warning("WRONG PASSWORD ENTERED BY "+name);
            rd.forward(req,res);
            return ;
        }

        req.removeAttribute("Message");
        //Session Creation
        HttpSession session = req.getSession();
        session.setAttribute("User", user);


        res.sendRedirect("home");
    }
}
