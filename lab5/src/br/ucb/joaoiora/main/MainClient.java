package br.ucb.joaoiora.main;

import br.ucb.joaoiora.client.TCPClient;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class MainClient {

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        client.connect();
    }

}
