package maxime.mica.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

public class ListDocuments extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileSystem fs = SandBoxFileSystem.getFileSystem();
        Path dir = Files.createDirectories(fs.getPath("/documents"));
        if (dir == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir);
        for (Path subpath : directoryStream) {
            resp.getWriter().println(subpath);
        }

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
