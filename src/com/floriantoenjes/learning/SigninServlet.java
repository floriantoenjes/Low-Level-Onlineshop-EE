package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Customer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;

@WebServlet(name = "SigninServlet", urlPatterns = "/signin")
public class SigninServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cookie_email = null;
        String cookie_password = null;

        response.setContentType("text/html;charset=UTF-8");

        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final HttpSession session = request.getSession();
        final Customer customer = (Customer) session.getAttribute("customer");

        final PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");

//        if (email.equals(customer.getEmail()) && password.equals(customer.getPassword())) {
//            out.println("Benutzer ist valide!");
//        } else {
//            out.println("Benutzer ist nicht valide!");
//        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("email")) {
                cookie_email = cookie.getValue();
            } else if (cookie.getName().equals("password")) {
                cookie_password = cookie.getValue();
            }
        }

        if (email.equals(cookie_email) && password.equals(cookie_password) ) {
            out.println("Benutzer ist valide!");
        } else {
            out.println("Benutzer ist nicht valide!");
        }

        ServletContext application = getServletContext();
        String jdbc_properties = application.getInitParameter("driver");
        final InputStream in = application.getResourceAsStream(jdbc_properties);
        final Properties p = new Properties();
        p.load(in);

        log(p.getProperty("driver"));

        out.println("</body>");
        out.println("</html>");
    }
}
