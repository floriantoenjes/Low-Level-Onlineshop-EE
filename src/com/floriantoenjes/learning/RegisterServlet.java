package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Customer;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Resource
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);

        try {
            persist(customer);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        if (customer.getId() != null) {
            request.setAttribute("message", "Die Registrierung war erfolgreich!");
        } else {
            request.setAttribute("message", "Die Registrierung war erfolglos!");
        }

        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

    private void persist(Customer customer) throws Exception {
        String[] autoGeneratedKeys = new String[]{"id"};
        final Connection con = dataSource.getConnection();
//        final Statement statement = con.createStatement();
//        statement.executeUpdate(
//                String.format("INSERT INTO onlineshop.customer (email, password) VALUES ('%s', '%s')",
//                        customer.getEmail(), customer.getPassword())
//        );

        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO onlineshop.customer (email, password) VALUES (?, ?)", autoGeneratedKeys);
        preparedStatement.setString(1, customer.getEmail());
        preparedStatement.setString(2, customer.getPassword());
        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        Long id = null;
        while (rs.next()) {
            id = rs.getLong(1);
            customer.setId(id);
        }
        con.close();
    }
}
