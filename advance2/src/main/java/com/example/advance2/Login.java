package com.example.advance2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

public class Login extends HttpServlet {
    String url = "jdbc:mysql://localhost:3306/estudent";
    String user = "root";
    String databasePassword = "IbnuN3z1f";
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String userEmail=request.getParameter("email");
        String userPassword=request.getParameter("password");

        try {
            Class. forName ("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, databasePassword);
            Statement statement = connection.createStatement();
            String emailQuery = "select * from students where email='"+userEmail+"'";


          ResultSet resultSet = statement.executeQuery(emailQuery);

          if (resultSet.next()) {
              if (resultSet.getString("password").equals(userPassword)) {
                  RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.html");
                  requestDispatcher.forward(request, response);
              }
              else{
                  response.getWriter().println("<h1>Incorrect password!<h1/>");
              }
          }
          else {
              response.getWriter().println("<h1>There is no such email please register!<h1/>");
          }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
