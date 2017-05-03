import java.net.Socket;

public class Client {
    
    /**
     * 
     */
    public static void main(String[] args) {
        // TODO Add handling for cmd arguments.
        Client client = new Client();
        client.connectTo("localhost", 7896);
    }

    /**
     * 
     */
    public void connectTo(final String ip, final int port) {
        try (Socket socket = new Socket(ip, port)) {
            
        } catch (IOException ex) {

        }
    }

}