package maxime.mica.servlet;


import maxime.mica.osgi.Activator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmbeddingJettyWithServlet {

    public static void main(String[] args) throws Exception {
        new Activator().start(null);
    }

    public static class HelloServlet extends HttpServlet {

        private static final long serialVersionUID = -6154475799000019575L;

        private static final String greeting = "Hello World";

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(greeting);
        }
    }

}