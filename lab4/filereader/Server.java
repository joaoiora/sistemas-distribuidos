import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    /**
     * 
     */
    private int port = 7896;

    /**
     * 
     */
    public static void main(String[] args) {
        // TODO Add handling for cmd arguments.
        Server server = new Server();
        server.start();
    }

    /**
     * 
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log("[SERVER] Server started and listening on port " + port);
            while (true) { // TODO Change for a word that terminates the connection.
                try (Socket socket = serverSocket.accept();
                        BufferedWriter input = new BufferedWriter(new PrintWriter(socket.getOutputStream(), true));
                        BufferedReader output = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    log("[SERVER] Client connected from IP: " + socket.getInetAddress().getLocalHost());
                    write(socket, "[SERVER] Please provide a path: ");
                    String fromUser = read(socket);
                    write(socket, proccessInput(fromUser));
                } catch (IOException socketEx) {
                    // TODO handle this later
                }
            }
        } catch (IOException serverSocketEx) {
            // TODO handle this later
        }
    }

    private void write(Socket socket, String message) {

        cleanup();
    }

    private String read(Socket socket) {

        cleanup();
    }

    private void cleanup() {

    }

    private static void log(final String message) {

    }

}