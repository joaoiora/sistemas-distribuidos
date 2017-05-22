/** Programa ServidorPotencia RMI **/

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServidorPotencia {

	public ServidorPotencia() throws RemoteException {
		super();
		try {
			Potencia p = new PotenciaImpl();
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Naming.rebind("rmi://192.168.0.90/Potencia", p);
		} catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	public static void main(String [] args){
		try {
			ServidorPotencia servpote = new ServidorPotencia();
		} catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}

}
