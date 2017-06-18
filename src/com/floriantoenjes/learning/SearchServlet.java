package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Item;

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
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

    @Resource
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String s = request.getParameter("search");
        try {
            List<Item> items = find(s);
            if (items != null) {
                HttpSession session = request.getSession();
                session.setAttribute("items", items);
            }
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
        dispatcher.forward(request, response);
    }

    public List<Item> find(String s) throws Exception {
        Connection con = dataSource.getConnection();
        PreparedStatement statement = con.prepareStatement("SELECT id, title, description, price, seller_id, buyer_id, sold " +
                "FROM onlineshop.item WHERE title like ?");
        if (s != null) {
            s = "%" + s + "%";
        }
        statement.setString(1, s);
        ResultSet rs = statement.executeQuery();
        List<Item> items = new ArrayList<>();
        while (rs.next()) {

            Item item = new Item();

            Long id = Long.valueOf(rs.getLong("id"));
            item.setId(id);

            String title = rs.getString("title");
            item.setTitle(title);

            String description = rs.getString("description");
            item.setDescription(description);

            double price = rs.getDouble("price");
            if (price != 0) {
                item.setPrice(Double.valueOf(price));
            }

            long seller_id = rs.getLong("seller_id");
            if (seller_id != 0) {
                item.setSeller_id(seller_id);
            }

            long buyer_id = rs.getLong("buyer_id");
            if (buyer_id != 0) {
                item.setBuyer_id(buyer_id);
            }

            Timestamp ts = rs.getTimestamp("sold");
            if (ts != null) {
                Date traded = new Date(ts.getTime());
                item.setTraded(traded);
            }

            items.add(item);
        }
        con.close();
        return items;
    }

}
