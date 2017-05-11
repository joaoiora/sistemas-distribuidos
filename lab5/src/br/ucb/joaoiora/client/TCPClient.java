package br.ucb.joaoiora.client;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;

import java.io.*;
import java.net.Socket;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class TCPClient {

    /**
     *
     */
    private String hostname = "localhost";

    /**
     *
     */
    private int port = 7896;

    /**
     *
     */
    private boolean fileReadyToTransfer = false;

    public TCPClient() {

    }

    public TCPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() {
        try (Socket socket = new Socket(hostname, port);
             ObjectOutputStream clientOut = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream clientIn = new ObjectInputStream(socket.getInputStream())) {
            startServerInteraction(clientIn, clientOut);

            /*
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            Message message = (ServerMessage) clientIn.readObject();
            System.out.println(message.getContent());

            line = stdin.readLine();
            Message clientMessage = new ClientMessage(line);
            clientOut.writeObject(clientMessage);

            message = (ServerMessage) clientIn.readObject();
            System.out.println(message.getContent());

            line = stdin.readLine();
            clientMessage = new ClientMessage(line);
            clientOut.writeObject(clientMessage);
            */
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void startServerInteraction(ObjectInputStream clientIn, ObjectOutputStream clientOut) throws IOException, ClassNotFoundException {
        Message receivedMessage = null;
        do {
            receivedMessage = (ServerMessage) readMessage(clientIn);
            System.out.println(receivedMessage.getContent());
            sendMessage(clientOut, new ClientMessage(readUserInput()));
        } while (true);
    }

    private static String readUserInput() {
        try (BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
            return stdin.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
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

}
