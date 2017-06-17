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
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }

    private void persist(Customer customer) throws Exception {
        final Connection con = dataSource.getConnection();
        final Statement statement = con.createStatement();
        statement.executeUpdate(
                String.format("INSERT INTO onlineshop.customer (email, password) VALUES ('%s', '%s')",
                        customer.getEmail(), customer.getPassword())
        );
    }
}
