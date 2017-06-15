package com.floriantoenjes.learning;

import jdk.internal.util.xml.impl.Input;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "SellServlet", urlPatterns = "/sell", asyncSupported = true)
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class SellServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        final Part part = request.getPart("foto");
//        final PrintWriter out = response.getWriter();
//
//        out.println("<!DOCTYPE html>");
//        out.println("<html>");
//        out.println("<head>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println(part.getSubmittedFileName());
//        out.println("</body>");
//        out.println("</html>");
//
//        final File outputFile = new File("/tmp/" + part.getSubmittedFileName());
//
//        final InputStream is = part.getInputStream();
//        final OutputStream os = new FileOutputStream(outputFile);

        final AsyncContext asyncContext = request.startAsync();
//        asyncContext.start(new FotoService(asyncContext));

        ServletInputStream servletInputStream = request.getInputStream();
        servletInputStream.setReadListener(new FotoReadListener(asyncContext));

    }

}
