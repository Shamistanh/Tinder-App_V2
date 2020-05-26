package web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {

    private final String subPath;

    public StaticServlet(String subPath) {
        this.subPath = subPath;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 1. parse req
         * 2. combine file location + parsed req
         * 3. read the file
         * 4. transfer that contents to the RESPONSE.BODY
         *
         * relative
         * Paths.get("a", "b", "c")     -> "a/b/c"
         * Paths.get("a/", "b", "c")    -> "a/b/c"
         * Paths.get("a", "/b", "c")    -> "a/b/c"
         * Paths.get("a/", "/b", "c")   -> "a/b/c"
         * Paths.get("a/", "/b", "//c") -> "a/b/c"
         *
         * HOWEVER:
         * absolute:
         * Paths.get("/a", "b", "c")    -> "/a/b/c"
         */
        String filename = req.getPathInfo();
        String osFileLocation = "content/templates";
        Path path = Paths.get(osFileLocation, subPath, filename);
        try (OutputStream os = resp.getOutputStream()) {
            Files.copy(path, os);
        }
    }

}
