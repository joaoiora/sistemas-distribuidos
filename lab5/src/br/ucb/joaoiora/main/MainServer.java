package br.ucb.joaoiora.main;

import br.ucb.joaoiora.server.TCPServer;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class MainServer {

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        server.run();
    }

}
