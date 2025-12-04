package com.example.Listeners;

import com.example.DBActions.Encoder;
import com.example.Model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("-> Entered into Connection Process.");

        ServletContext sc = sce.getServletContext();

//        String uri = Encoder.decode(sc.getInitParameter("DB_URI"))+Encoder.decode("TGlicmFyeU1hbmdlbWVudFN5c3RlbQ==");
//        String username = Encoder.decode(sc.getInitParameter("DB_USERNAME"));
//        String password = Encoder.decode(sc.getInitParameter("DB_PASSWORD"));
//
//        System.out.println(uri+" "+username+" "+password);
//        Connection con = null;

        Connection con;

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            DataSource ds = (DataSource) envCtx.lookup("jdbc/mydb");
            con = ds.getConnection();
//
//            Class.forName("org.postgresql.Driver"); // Recommended
//            con = DriverManager.getConnection(uri, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (con != null) {
            sc.setAttribute("DBConnection",con);
            System.out.println("-> Connection Established.");
        } else {
            System.out.println("-> Connection Failed.");
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection con = (Connection) sce.getServletContext().getAttribute("DBConnection");
        if (con != null) {
            try {
                con.close();
                System.out.println("-> Connection Closed.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
