package com.floriantoenjes.learning;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    private DataSource ds;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final PrintWriter writer = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");

        writer.println("<!DOCTYPE html>");
        writer.println("<html><body>");

        try {
            ds = (DataSource) InitialContext.doLookup("jdbc/__default");
            final Connection con = ds.getConnection();
            if (con.isValid(10)) {
                writer.println("<br/>Connected!");
            }
            con.close();
        } catch (Exception e) {
            writer.println(e.getMessage());
        }

        writer.println("</br>Finished!</body></html>");
    }
}
