/** Programa Potencia RMI - Interface **/

import java.rmi.*;

public interface Potencia extends Remote {

	public double calculaPotencia(double base, double expo) throws RemoteException;

}
