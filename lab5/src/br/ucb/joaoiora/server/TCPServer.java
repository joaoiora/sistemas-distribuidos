package br.ucb.joaoiora.server;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;
import br.ucb.joaoiora.utils.StringUtils;

import java.io.*;
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
    private String requestedFilename;

    /**
     *
     */
    private boolean readyToTransfer = false;

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
                startClientInteraction(serverIn, serverOut);
                if (this.readyToTransfer) {
                    // start the thread to send requested file...
                }
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
     *
     * @param serverIn
     * @param serverOut
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void startClientInteraction(ObjectInputStream serverIn, ObjectOutputStream serverOut) throws IOException, ClassNotFoundException {
        Message receivedMessage = null;
        boolean validFile = false;
        do {
            sendMessage(serverOut, new ServerMessage("Please enter a directory: "));
            receivedMessage = (ClientMessage) readMessage(serverIn);
            System.out.println("Client requested directory: " + receivedMessage.getContent());
            if (isRegularFile(getPath(receivedMessage.getContent()))) {
                prepFileTransfer(serverOut, receivedMessage);
                return; // There should be a better way to exit this (not for now)...
            }
            sendMessage(serverOut, createResponse(receivedMessage));
            receivedMessage = (ClientMessage) readMessage(serverIn);
            System.out.println("Client requested the file: " + receivedMessage.getContent());
            validFile = isRegularFile(getPath(receivedMessage.getContent()));
        } while (!validFile);
        prepFileTransfer(serverOut, receivedMessage);
    }

    /**
     *
     * @param serverOut
     * @param receivedMessage
     * @throws IOException
     */
    private void prepFileTransfer(ObjectOutputStream serverOut, Message receivedMessage) throws IOException {
        this.requestedFilename = receivedMessage.getContent();
        this.readyToTransfer = true;
        sendMessage(serverOut, Boolean.valueOf(readyToTransfer));
    }

    /**
     *
     * @param serverOut
     * @param message
     * @throws IOException
     */
    private void sendMessage(ObjectOutputStream serverOut, Message message) throws IOException {
        serverOut.writeObject(message);
    }

    private void sendMessage(ObjectOutputStream serverOut, Boolean value) throws IOException {
        serverOut.writeBoolean(value);
    }

    /**
     *
     * @param serverIn
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Message readMessage(ObjectInputStream serverIn) throws IOException, ClassNotFoundException {
        return (Message) serverIn.readObject();
    }

    /**
     *
     * @param receivedMessage
     * @return
     */
    private Message createResponse(final Message receivedMessage) {
        final Path rootPath = getPath(receivedMessage.getContent());
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootPath)) {
            Message message = new ServerMessage();
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    message.append(path.toString(), true);
                }
                message.append("Please enter the name of the desired file: ", false);
                return message;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ServerMessage("There was a problem trying to parse your request.");
    }

    /**
     *
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
     *
     * @param path
     * @return
     */
    private static Path getPath(final String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        return Paths.get(path);
    }

    /**
     *
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
