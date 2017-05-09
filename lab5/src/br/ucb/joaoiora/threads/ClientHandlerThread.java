package br.ucb.joaoiora.threads;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;
import br.ucb.joaoiora.utils.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class ClientHandlerThread implements Runnable {

    private Socket socket;

    private String directory;

    private String requestedFile;

    public ClientHandlerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("[DEBUG] Started ClientHandlerThread for socket " + socket.getInetAddress());
        try (ObjectOutputStream serverOut = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream serverIn = new ObjectInputStream(socket.getInputStream())) {
            dealWithClientInput(serverOut, serverIn);


        } catch (IOException | ClassNotFoundException ex) {

        }
        System.out.println("[DEBUG] Finished running ClientHandlerThread for socket " + socket.getInetAddress());
    }

    private void dealWithClientInput(ObjectOutputStream serverOut, ObjectInputStream serverIn) throws IOException, ClassNotFoundException {
        serverOut.writeObject(new ServerMessage("Please enter a directory: "));
        Message message = (ClientMessage) serverIn.readObject();
        serverOut.writeObject(createRespondeFromInput(message.getContent()));
    }

    private static Message createRespondeFromInput(final String messageContent) {
        if (StringUtils.isEmpty(messageContent)) {
            return new ServerMessage("You did not provide a directory.");
        }
        File file = new File(messageContent);
        if (file.isFile()) {
            // deal later with this...
            return null;
        }
        if (file.isDirectory()) {
            return getListOfFiles(file.listFiles());
        }
        return new ServerMessage("There was a problem when parsing your input.");
    }

    private static Message getListOfFiles(final File[] files) {
        Message message = new ServerMessage();
        for (File file : files) {
            message.append(file.getName());
        }
        return message;
    }


}
