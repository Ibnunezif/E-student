package com.example.advance2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class EvaluateAnswer extends HttpServlet {
    String url = "jdbc:mysql://localhost:3306/estudent";
    String user = "root";
    String databasePassword = "IbnuN3z1f";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String question1 = req.getParameter("question1");
        String question2 = req.getParameter("question2");
        String question3 = req.getParameter("question3");
        String question4 = req.getParameter("question4");
        String question5 = req.getParameter("question5");

        String[] userAnswers = {question1, question2, question3, question4, question5};

        int grade = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, databasePassword);
            Statement statement = connection.createStatement();
            String answers = "select answer from question_and_answer where course_id='" + 1 + "'";
            ResultSet resultSet = statement.executeQuery(answers);
            int i = 0;
            while (resultSet.next()) {
                if (resultSet.getString("answer").equals(userAnswers[i].toLowerCase())) {
                    grade++;
                }
                i++;
            }

            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("userEmail");

            String writeSQL=    "insert into result (student_email,course_name,grade) values ("+email+","+"web development"+","+grade+");";
            statement.executeUpdate(writeSQL);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        RequestDispatcher dispatcher = req.getRequestDispatcher("home.html");
        dispatcher.forward(req,res);

    }
}
