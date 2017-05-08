package br.ucb.joaoiora.server;

import br.ucb.joaoiora.threads.ClientHandlerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class Server {

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
    public Server() {

    }

    /**
     *
     * @param serverPort
     */
    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     *
     */
    public void run() {
        startService();
        while (isRunning()) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                if (!isRunning()) {
                    System.err.println("The server is not running.");
                    System.exit(-1);
                }
                throw new RuntimeException("[ERROR] A client could not connect to the server.", e);
            }
            new Thread(new ClientHandlerThread(socket)).start();
        }
        System.out.println("The server has stopped running.");
    }

    /**
     *
     * @return
     */
    private synchronized boolean isRunning() {
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
