package firsthttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author Lars Mortensen
 */
public class FirstHtttpServer {

    static int port = 8080;
    static String ip = "localhost";

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/welcome", new RequestHandler());
        server.createContext("/Headers", new RequestHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server started, listening on port: " + port);
    }

    static class RequestHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {

            StringBuilder sb = new StringBuilder();

            // The map
            Map content = he.getRequestHeaders();

            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");

            sb.append("<table border = 1px>");
            
            sb.append("<tr>");
            sb.append("<td> <h1>Headers</h1> </td>");
            sb.append("<td> <h1>Values</h1> </td>");
            sb.append("</tr>");
            

            for (Object key : content.keySet()) {
                String keyValue = key.toString();
                String value = content.get(key).toString();
                sb.append("<tr>");
                sb.append("<td>" + keyValue + "</td>");
                sb.append("<td>" + value + "</td>");
                sb.append("</tr>");
            }

            sb.append("</table>");

            sb.append("<h2>Welcome to my very first home made Web Server :-)</h2>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");

            String response = sb.toString();

            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");

            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); // Try with resources
            }
        }
    }
}

// Something saved
//            sb.append("<!DOCTYPE html>\n");
//            sb.append("<html>\n");
//            sb.append("<head>\n");
//            sb.append("<title>My fancy Web Site</title>\n");
//            sb.append("<meta charset='UTF-8'>\n");
//            sb.append("</head>\n");
//            sb.append("<body>\n");
//            sb.append("<h2>Welcome to my very first home made Web Server :-)</h2>\n");
//            sb.append("</body>\n");
//            sb.append("</html>\n");
