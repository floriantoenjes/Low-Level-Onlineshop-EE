package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);

//        final HttpSession session = request.getSession();
//        session.setAttribute("customer", customer);
        response.addCookie(new Cookie("email", email));
        response.addCookie(new Cookie("password", password));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.html");
        requestDispatcher.forward(request, response);
    }
}
