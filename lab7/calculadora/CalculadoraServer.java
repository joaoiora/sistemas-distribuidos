package br.ucb.joaoiora.server;

import br.ucb.joaoiora.model.Calculadora;
import br.ucb.joaoiora.model.CalculadoraImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by joaocarlos on 22/05/17.
 */
public class CalculadoraServer {

    public CalculadoraServer() throws RemoteException {
        super();
        try {
            Calculadora calculadora = new CalculadoraImpl();
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("rmi://192.168.0.90/Calculadora", calculadora);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        CalculadoraServer server = new CalculadoraServer();
    }

}
