package com.floriantoenjes.learning;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "FotoServlet", urlPatterns = "/foto")
public class FotoServlet extends HttpServlet {

    @Resource
    private DataSource dataSource;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String id = request.getParameter("id");

            Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT foto FROM onlineshop.item WHERE id = ?");
            statement.setLong(1, Long.parseLong(id));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Blob foto = rs.getBlob("foto");
                response.reset();
                int length = (int) foto.length();
                response.setHeader("Content-Length", String.valueOf(length));
                InputStream in = foto.getBinaryStream();
                final int bufferSize = 256;
                byte[] buffer = new byte[bufferSize];

                ServletOutputStream out = response.getOutputStream();
                while ((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.flush();
                foto = null;
            }
            statement.close();
            con.close();

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}
