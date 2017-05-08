package br.ucb.joaoiora.main;

import br.ucb.joaoiora.server.Server;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class MainServer {

    public static void main(String[] args) {

        Server server = new Server();
        server.run();
    }

}
