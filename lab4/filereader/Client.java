import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    /**
     * 
     */
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 7896;
        if (args.length != 2) {
            System.err.println("usage: Client <hostname> <port>");
            System.err.println("Invalid number of arguments, will use as default " + hostname + ":" + port + ".");
        } else {
            hostname = args[0];
            port = getPort(args[1]);
        }
        Client client = new Client();
        client.connectTo(hostname, port);
    }

    /**
     * 
     */
    public void connectTo(final String ip, final int port) {
        try (Socket socket = new Socket(ip, port);
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.print(input.readLine());
            output.println(getInput());
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {

        }
    }

    /**
     * 
     */
    private String getInput() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            return input.readLine();
        } catch (IOException e) {
            log("[CLIENT] ");
            log(e.getMessage());
        }
        return null;
    }

    /**
     * 
     */
    private static void log(final String message) {
        System.out.println(message);
    }

    /**
     * 
     */
    private static int getPort(String args) {
        try {
            return Integer.parseInt(args);
        } catch (NumberFormatException ex) {
            System.err.println("Port Number must be an integer.");
            System.exit(-1);
        }
        return -1;
    }

}