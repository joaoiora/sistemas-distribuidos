/** Programa Potencia RMI - Implementacao **/

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PotenciaImpl extends UnicastRemoteObject implements Potencia{

	public PotenciaImpl() throws RemoteException {
		super();
	}

	public double calculaPotencia(double base, double expo) throws RemoteException {
		return Math.pow(base,expo);
	}

}
