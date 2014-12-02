package maxime.mica.servlet;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public final class SandBoxFileSystem {
    private static FileSystem fs;

    private SandBoxFileSystem() {
    }

    public static synchronized FileSystem getFileSystem() throws IOException {
        if (fs == null || !fs.isOpen()) {
            Map<String, String> env = new TreeMap<>();
            env.put("create", "true");
            env.put("encoding", "UTF-8");
            Path path = Paths.get(System.getProperty("user.dir"), "content.zip");
            URI zipDisk = URI.create("jar:file:" + path.toAbsolutePath().toUri().getPath());
            fs = FileSystems.newFileSystem(zipDisk, env);
        }
        return fs;
    }
}
