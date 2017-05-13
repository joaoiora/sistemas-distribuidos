package br.ucb.joaoiora.server;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;
import br.ucb.joaoiora.utils.CollectionUtils;
import br.ucb.joaoiora.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class TCPServer {

    private static final boolean IS_WINDOWS = System.getProperty("os.name").contains("indows");

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
    private String requestedFilename;

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
                System.out.println("A new client from " + socket.getInetAddress().getHostAddress() + " joined the Server.");
                startClientInteraction(serverIn, serverOut);

                File file = new File(requestedFilename);
                byte[] fileContent = Files.readAllBytes(getPath(requestedFilename));

                // TODO check file size, prevent files over 512kb because UDP
                // User has requested a file
                // Encapsulate needed data on MyFile
                // filename, source folder, dest. folder, content (bytes)
                // send object through UDP connection
                //   create new DatagramSocket
                //   client connects to it
                //   client receives content of file
                //   saves file on dest. folder

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param serverIn
     * @param serverOut
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void startClientInteraction(ObjectInputStream serverIn, ObjectOutputStream serverOut) throws IOException, ClassNotFoundException {
        Message receivedMessage = null;
        sendMessage(serverOut, new ServerMessage("Please enter a directory: "));
        receivedMessage = (ClientMessage) readMessage(serverIn);
        System.out.println("Client requested directory: " + receivedMessage.getContent());
        /* TODO do user input validation
        if (isRegularFile(getPath(receivedMessage.getContent()))) {
            prepFileTransfer(serverOut, receivedMessage);
            return; // There should be a better way to exit this (not for now)...
        }
        */
        sendMessage(serverOut, createResponse(receivedMessage));
        receivedMessage = (ClientMessage) readMessage(serverIn);
        System.out.println("Client requested the file: " + receivedMessage.getContent());
        this.requestedFilename = receivedMessage.getContent();
    }

    /**
     * @param serverOut
     * @param message
     * @throws IOException
     */
    private void sendMessage(ObjectOutputStream serverOut, Message message) throws IOException {
        serverOut.writeObject(message);
    }

    /**
     * @param serverIn
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Message readMessage(ObjectInputStream serverIn) throws IOException, ClassNotFoundException {
        return (Message) serverIn.readObject();
    }

    /**
     * @param receivedMessage
     * @return
     */
    private Message createResponse(final Message receivedMessage) {
        final Path path = getPath(receivedMessage.getContent());
        final List<String> paths = getListOfFiles(path);
        if (CollectionUtils.isEmpty(paths)) {
            return new ServerMessage("There was a problem when trying to parse your input");
        }
        Message message = new ServerMessage();
        for (String str : paths) {
            message.append(str, true);
        }
        message.append("Please enter the complete path of the desired file: ", false);
        return message;
    }

    /**
     *
     * @param path
     * @return
     */
    private static List<String> getListOfFiles(final Path path) {
        return getListOfFiles(path, 512);
    }

    /**
     *
     * @param path
     * @param maxSize
     * @return
     */
    private static List<String> getListOfFiles(final Path path, int maxSize) {
        List<String> files = new ArrayList<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            for (Path p : paths) {
                if (!Files.isDirectory(p)) {
                    files.add(p.toString());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return files;
    }

    /**
     * @param path
     * @return
     */
    private static boolean isRegularFile(final Path path) {
        if (path == null) {
            return false;
        }
        return Files.isRegularFile(path);
    }

    /**
     * @param path
     * @return
     */
    private static Path getPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (IS_WINDOWS) {
            path = path.replace("\\", "\\\\");
        }
        return Paths.get(path);
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
