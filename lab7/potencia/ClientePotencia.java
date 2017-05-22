/** Programa ClientePotencia RMI **/

import java.rmi.Naming;

public class ClientePotencia {

	public static void main(String[] args) {
		try {
			Potencia clipote = (Potencia) Naming.lookup("rmi://" + args[0] + "/Potencia");
			System.out.println("Resultado de " + args[1] + " elevado a " + args[2] + " = "
				+ clipote.calculaPotencia(new Double(args[1]), new Double(args[2])));
		} catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}

}
