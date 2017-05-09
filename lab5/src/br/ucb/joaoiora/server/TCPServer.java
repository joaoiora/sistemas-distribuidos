package br.ucb.joaoiora.server;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class TCPServer {

    /**
     *
     */
    private ServerSocket serverSocket;

    /**
     *
     */
    private int serverPort = 7896;

    /**
     *
     */
    private boolean running;

    /**
     *
     */
    public TCPServer() {

    }

    /**
     * @param serverPort
     */
    public TCPServer(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     *
     */
    public void run() {
        startService();
        while (true) {
            try (Socket socket = serverSocket.accept();
                 ObjectOutputStream serverOut = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream serverIn = new ObjectInputStream(socket.getInputStream())) {
                // TODO later, move 'conversation' to a thread
                serverOut.writeObject(new ServerMessage("Please enter a directory: "));
                Message message = (ClientMessage) serverIn.readObject();
                System.out.println(message.getContent());
                serverOut.writeObject(createResponse(message.getContent()));
                message = (ClientMessage) serverIn.readObject();
                System.out.println("User requested: " + message.getContent()); // TODO try to keep a reference to desired file
                // TODO check file size, prevent files over 512kb because UDP

                // User has requested a file
                // Encapsulate needed data on MyFile
                // filename, source folder, dest. folder, content (bytes)
                // send object through UDP connection
                //   create new DatagramSocket
                //   client connects to it
                //   client receives content of file
                //   saves file on dest. folder


            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Message createResponse(String content) {
        final Path root = Paths.get(content);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {
            Message message = new ServerMessage();
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    message.append(path.toString());
                    message.append(System.lineSeparator());
                }
            }
            message.append("Please enter the name of the desired file: ");
            return message;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    private boolean isRunning() {
        return running;
    }

    /**
     *
     */
    private void startService() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is up and running on port " + serverPort);
        } catch (IOException ex) {
            throw new RuntimeException("[ERROR] Can't start the service on port " + serverPort, ex);
        }
    }

    /**
     *
     */
    public void stop() {
        this.running = false;
        try {
            serverSocket.close();
            System.out.println("");
        } catch (IOException ex) {
            throw new RuntimeException("[ERROR] Can't close the server.", ex);
        }
    }

}
