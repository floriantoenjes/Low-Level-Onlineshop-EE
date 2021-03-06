package com.floriantoenjes.learning;

import com.floriantoenjes.learning.model.Customer;
import com.floriantoenjes.learning.model.Item;
import com.sun.org.apache.regexp.internal.RE;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "SellServlet", urlPatterns = "/sell")
@MultipartConfig(location = "/tmp",
        maxFileSize = 1024 * 1024 * 10,
        fileSizeThreshold = 1024 * 1024,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class SellServlet extends HttpServlet {

    public final static int MAX_IMAGE_LENGTH = 400;

    @Resource
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part part = request.getPart("foto");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            InputStream in = part.getInputStream();
            int i = 0;
            while ((i = in.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            throw new ServletException(e.getMessage());
        }

        HttpSession session = request.getSession();
        Object customer = session.getAttribute("customer");

        if (customer != null) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String price = request.getParameter("price");

            Item item = new Item();
            item.setTitle(title);
            item.setDescription(description);
            item.setPrice(Double.valueOf(price));
            item.setSeller_id(((Customer) customer).getId());
            item.setFoto(baos.toByteArray());
            baos.flush();

            try {
                persist(item);
            } catch (Exception e) {
                throw new ServletException(e.getMessage());
            }
        }
    }

    private void persist(Item item) throws Exception {
        String[] autoGeneratedKeys = new String[]{"id"};
        Connection con = dataSource.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO onlineshop.item " +
                "(title, description, price, foto, seller_id) " +
                "VALUES (?, ?, ?, ?, ?)", autoGeneratedKeys);
        preparedStatement.setString(1, item.getTitle());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setDouble(3, item.getPrice());
        preparedStatement.setBytes(4, scale(item.getFoto()));
        preparedStatement.setLong(5, item.getSeller_id());
        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        Long id = null;
        while (rs.next()) {
            id = rs.getLong(1);
            item.setId(id);
        }
        con.close();
    }

    public byte[] scale(byte[] foto) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(foto);
        BufferedImage originalBufferedImage = ImageIO.read(byteArrayInputStream);

        double originalWidth = (double) originalBufferedImage.getWidth();
        double originalHeight = (double) originalBufferedImage.getHeight();

        double relevantLength = originalWidth > originalHeight ? originalWidth : originalHeight;

        double scaleFactor = MAX_IMAGE_LENGTH / relevantLength;

        int width = (int) Math.round(originalWidth * scaleFactor);
        int height = (int) Math.round(originalHeight * scaleFactor);

        BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedBufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        AffineTransform affineTransform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        g2d.drawRenderedImage(originalBufferedImage, affineTransform);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedBufferedImage, "PNG", baos);

        return baos.toByteArray();
    }

}
