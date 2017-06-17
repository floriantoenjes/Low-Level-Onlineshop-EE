package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Customer;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@WebServlet(name = "SigninServlet", urlPatterns = "/signin")
public class SigninServlet extends HttpServlet {

    @Resource
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _email = request.getParameter("email");
        String _password = request.getParameter("password");

        Customer customer = null;
        try {
            customer = find(_email, _password);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        if (customer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    public Customer find(String _email, String _password) throws Exception {
        Connection con = dataSource.getConnection();
//        Statement statement = con.createStatement();
//        ResultSet resultSet = statement.executeQuery(String.format("SELECT id, email, password FROM onlineshop.customer " +
//                "WHERE email = '%s' AND password = '%s'", _email, _password));
        PreparedStatement preparedStatement = con.prepareStatement("SELECT id, email, password FROM onlineshop.customer WHERE email = ? AND password = ?");
        preparedStatement.setString(1, _email);
        preparedStatement.setString(2, _password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {

            Customer customer = new Customer();

            Long id = resultSet.getLong("id");
            customer.setId(id);

            String email = resultSet.getString("email");
            customer.setEmail(email);

            String password = resultSet.getString("password");
            customer.setPassword(password);

            return customer;
        }
        con.close();
        return null;
    }
}
