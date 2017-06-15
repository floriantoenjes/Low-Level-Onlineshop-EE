package com.floriantoenjes.learning;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

public class FotoReadListener implements ReadListener {
    private AsyncContext asyncContext;

    public FotoReadListener(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void onDataAvailable() throws IOException {
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
            request.getServletContext().log("LOG");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                asyncContext.complete();
            }
        }
    }

    @Override
    public void onAllDataRead() throws IOException {
        asyncContext.complete();
    }

    @Override
    public void onError(Throwable ex) {
        ex.printStackTrace();
        asyncContext.complete();
    }
}
