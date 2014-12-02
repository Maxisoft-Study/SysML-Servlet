package maxime.mica.servlet;


import maxime.mica.osgi.Activator;

public class EmbeddingJettyWithServlet {

    public static void main(String[] args) throws Exception {
        new Activator().start(null);
    }
}