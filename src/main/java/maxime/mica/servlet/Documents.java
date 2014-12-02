package maxime.mica.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Documents extends HttpServlet {

    private static Path prepareFilePath(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FileSystem fs = SandBoxFileSystem.getFileSystem();
        Path file = Files.createDirectories(fs.getPath("/documents", req.getPathInfo()));
        if (file == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        if (Files.notExists(file) || Files.isDirectory(file)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return file;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path file = prepareFilePath(req, resp);
        if (file != null) {
            Files.copy(file, resp.getOutputStream());
            resp.setContentType("application/xml");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path file = prepareFilePath(req, resp);
        if (file != null) {
            Files.copy(req.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path file = prepareFilePath(req, resp);
        if (file != null) {
            Files.delete(file);
        }
    }
}
