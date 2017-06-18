package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Customer;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "BuyServlet", urlPatterns = "/buy")
public class BuyServlet extends HttpServlet {

    @Resource
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long item_id = Long.parseLong(request.getParameter("item_id"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        try {
            update(item_id, customer.getId());
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/buy.jsp");
        dispatcher.forward(request, response);
    }

    private void update(Long item_id, Long id) throws Exception {
        Connection con = dataSource.getConnection();
        PreparedStatement statement = con.prepareStatement(
                "UPDATE onlineshop.item SET buyer_id = ?, sold = SYSTIMESTAMP WHERE id = ?"
        );
        statement.setLong(1, id);
        statement.setLong(2, item_id);
        statement.executeUpdate();
        con.close();
    }

}
