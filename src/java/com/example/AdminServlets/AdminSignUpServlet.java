package com.example.AdminServlets;

import com.example.DBActions.UserCRUD;
import com.example.Model.User;
import com.example.DBActions.Encoder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

public class AdminSignUpServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        con = (Connection) sc.getServletContext().getAttribute("DBConnection");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/assets/jsp/AdminSignUp.jsp");
        rd.forward(req,res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        //Get Request Body
        String name = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String cpassword = req.getParameter("conform_password");

        //Check Password
        if(!password.equals(cpassword)){
            RequestDispatcher rd = req.getRequestDispatcher("/assets/jsp/AdminSignUp.jsp");
            req.setAttribute("Message","Password Doesn't Match.");
            rd.forward(req,res);
            return ;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";         // no spaces + min 8 length

        if (!password.matches(regex)) {
            RequestDispatcher rd = req.getRequestDispatcher("/assets/jsp/AdminSignUp.jsp");
            req.setAttribute("Message","Password Insefficient!");
            rd.forward(req,res);
            return ;
        }

        req.removeAttribute("Message");
        //Encode & Insert into DB
        String passKey = Encoder.encode(password);
        User user = new User(name,email,passKey,"admin");
        user = UserCRUD.addUser(con,user);

        //Session Creation
        HttpSession session = req.getSession();
        session.setAttribute("User", user);

        //Print
        if(user==null){
            RequestDispatcher rd = req.getRequestDispatcher("/assets/jsp/SignUp.jsp");
            req.setAttribute("Message","User Name Already Exisit!");
            rd.forward(req,res);
            return ;
        }
        res.sendRedirect("home");
    }
}
