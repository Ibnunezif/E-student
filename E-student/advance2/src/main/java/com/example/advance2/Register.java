package com.example.advance2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Register extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/estudent";
    String user = "root";
    String databasePassword = "IbnuN3z1f";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName=request.getParameter("firstName");
        String lastName=request.getParameter("lastName");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String password=request.getParameter("password");
        Date dob= Date.valueOf(request.getParameter("dob"));
        String gender=request.getParameter("gender");
        String address=request.getParameter("address");
        String confirmPassword =request.getParameter("confirmPassword");


                 if (Objects.equals(password, confirmPassword)){
                     try {
                         Class. forName ("com.mysql.cj.jdbc.Driver");
                         Connection connection = DriverManager.getConnection(url, user, databasePassword);

                         String sql = "INSERT INTO students (firstName, lastName, email, phone, password, dob, gender, address) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                         PreparedStatement statement = connection.prepareStatement(sql);

                         statement.setString(1, firstName);
                         statement.setString(2, lastName);
                         statement.setString(3, email);
                         statement.setString(4, phone);
                         statement.setString(5, password);
                         statement.setDate(6, new java.sql.Date(dob.getTime())); // Convert java.util.Date to java.sql.Date
                         statement.setString(7, gender);
                         statement.setString(8, address);


                         int rowsInserted = statement.executeUpdate();

                         if (rowsInserted > 0) {
                             System.out.println("User data inserted successfully.");
                         } else {
                             System.out.println("Failed to insert user data.");
                         }

                         statement.close();
                         connection.close();

                     } catch (ClassNotFoundException | SQLException e) {
                         throw new RuntimeException(e);
                     }
                     RequestDispatcher rd=request.getRequestDispatcher("home.html");
                     rd.forward(request,response);
                 }



    }
}
