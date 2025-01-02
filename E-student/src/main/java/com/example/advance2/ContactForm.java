package com.example.advance2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactForm extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/estudent";
    String user = "root";
    String databasePassword = "IbnuN3z1f";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");


        try {
            Class. forName ("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, databasePassword);

            String sql = "INSERT INTO contactForm (name, email, subject, message) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, subject);
            statement.setString(4, message);

            statement.executeUpdate();


            statement.close();
            connection.close();

            response.sendRedirect("index.jsp");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
