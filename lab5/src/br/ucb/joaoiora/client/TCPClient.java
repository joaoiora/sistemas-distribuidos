package br.ucb.joaoiora.client;

import br.ucb.joaoiora.model.ClientMessage;
import br.ucb.joaoiora.model.FileObject;
import br.ucb.joaoiora.model.Message;
import br.ucb.joaoiora.model.ServerMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
    public TCPClient() {

    }

    /**
     * @param hostname
     * @param port
     */
    public TCPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     *
     */
    public void connect() {
        try (Socket socket = new Socket(hostname, port);
             ObjectOutputStream clientOut = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream clientIn = new ObjectInputStream(socket.getInputStream())) {
            startServerInteraction(clientIn, clientOut);
            try (DatagramSocket datagramSocket = new DatagramSocket(30000)) {
                byte[] incomingData = new byte[1024 * 1000 * 50];
                while (true) {
                    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                    datagramSocket.receive(incomingPacket);
                    byte[] data = incomingPacket.getData();
                    ByteArrayInputStream bais = new ByteArrayInputStream(data);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    FileObject fileObject = (FileObject) ois.readObject();
                    if (fileObject == null) {
                        System.out.println("There was an error when trying to obtain the file.");
                        System.exit(-1);
                    }
                    createAndWriteFile(fileObject);
                    InetAddress address = incomingPacket.getAddress();
                    int port = incomingPacket.getPort();
                    byte[] reply = "Thanks for the file.".getBytes();
                    datagramSocket.send(new DatagramPacket(reply, reply.length, address, port));
                    Thread.sleep(4000);
                    System.exit(0);
                }
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param clientIn
     * @param clientOut
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void startServerInteraction(ObjectInputStream clientIn, ObjectOutputStream clientOut) throws IOException, ClassNotFoundException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        Message receivedMessage = null;
        receivedMessage = (ServerMessage) readMessage(clientIn);
        System.out.println(receivedMessage.getContent());
        line = stdin.readLine();
        sendMessage(clientOut, new ClientMessage(line));

        receivedMessage = (ServerMessage) readMessage(clientIn);
        System.out.println(receivedMessage.getContent());
        line = stdin.readLine();
        sendMessage(clientOut, new ClientMessage(line));
        stdin.close();
    }

    /**
     * @param fileObject
     */
    private void createAndWriteFile(FileObject fileObject) {
        String output = fileObject.getDestFolder() + fileObject.getFilename();
        try {
            File file = new File(output);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(fileObject.getContent());
                fos.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Output file " + fileObject.getFilename() + " is saved to temp folder of the project.");
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

}
