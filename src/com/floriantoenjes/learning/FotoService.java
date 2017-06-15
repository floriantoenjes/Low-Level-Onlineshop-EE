package com.floriantoenjes.learning;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

public class FotoService implements Runnable {

    private AsyncContext asyncContext;


    public FotoService(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {

        final HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
        final HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

        PrintWriter out = null;

        try {
            out = response.getWriter();

            final Part part = request.getPart("foto");
            InputStream is = part.getInputStream();
            OutputStream os = new FileOutputStream(new File("/tmp/" + part.getSubmittedFileName()));

            byte[] b = new byte[1024];
            int i = 0;

            while ((i = is.read(b)) != -1) {
                os.write(b);
            }
            os.flush();
            out.write("true");
            asyncContext.complete();
        } catch (Exception e) {
            out.write("false");
            e.printStackTrace();
        }
    }
}
