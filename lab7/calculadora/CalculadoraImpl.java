package br.ucb.joaoiora.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by joaocarlos on 22/05/17.
 */
public class CalculadoraImpl extends UnicastRemoteObject implements Calculadora {

    /**
     *
     * @throws RemoteException
     */
    public CalculadoraImpl() throws RemoteException {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int soma(int x, int y) throws RemoteException {
        return x + y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int sub(int x, int y) throws RemoteException {
        return x - y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int mult(int x, int y) throws RemoteException {
        return x * y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int div(int x, int y) throws RemoteException {
        if (x == 0 || y == 0) {
            return 0;
        }
        return x / y;
    }

}
