import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Simple HTTP Server Example (Single Threaded)
 *
 * Purpose:
 * This program demonstrates the fundamental mechanics behind how a web server
 * works using only core Java networking classes.
 *
 * The goal of this example is educational. It shows students how an HTTP request
 * arrives through a TCP socket and how a server can manually process the request
 * headers and send back a valid HTTP response.
 *
 * What this example does:
 * - Opens a ServerSocket listening on port 8055
 * - Waits for a client connection (browser, curl, etc.)
 * - Reads the HTTP request headers sent by the client
 * - Stores the headers in a text file called "headers.txt"
 * - Sends a minimal HTTP response: status 200 with body "ok"
 *
 * Important notes:
 * - This server is intentionally single-threaded.
 * - It processes one request at a time.
 * - It is NOT intended for production use.
 * - It exists purely to demonstrate how HTTP communication works at a low level.
 *
 * Concepts demonstrated:
 * - TCP sockets
 * - HTTP request structure
 * - Header parsing
 * - Writing server responses manually
 */

public class SimpleWebServer {

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(8055);
        System.out.println("Simple HTTP Server running on port 8055");

        while (true) {

            Socket client = server.accept();
            IO.println("Client connected: " + client.getInetAddress());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));

            PrintWriter out = new PrintWriter(client.getOutputStream());

            FileWriter log = new FileWriter("headers.txt", true);

            String line;

            // Read HTTP request headers
            while ((line = in.readLine()) != null && !line.isEmpty()) {

                IO.println(line);
                log.write(line + "\n");

            }

            log.write("\n-----------------------------\n\n");
            log.close();

            // Send minimal HTTP response
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/plain");
            out.println();
            out.println("ok");

            out.flush();

            client.close();
        }
    }
}