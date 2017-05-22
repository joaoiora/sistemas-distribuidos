package br.ucb.joaoiora.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by joaocarlos on 22/05/17.
 */
public interface Calculadora extends Remote {

    /**
     *asdasdasasdasas
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    int soma(int x, int y) throws RemoteException;

    /**
     *
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    int sub(int x, int y) throws RemoteException;

    /**
     *
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    int mult(int x, int y) throws RemoteException;

    /**
     *
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    int div(int x, int y) throws RemoteException;

}
