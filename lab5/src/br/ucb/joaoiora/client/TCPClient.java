package br.ucb.joaoiora.client;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;
import br.ucb.joaoiora.utils.ConsoleUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class TCPClient {

    private String hostname = "localhost";
    private int port = 7896;

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
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
