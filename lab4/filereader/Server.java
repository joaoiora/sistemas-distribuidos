import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/**
	 * 
	 */
	public static void main(String[] args) {
		int port = 7896;
		if (args.length != 1) {
			System.err.println("usage: Server <portnumber>");
			System.err.println("Invalid number of arguments, will use as default port " + port + ".");
		} else {
			port = getPort(args[0]);
		}
		Server server = new Server();
		server.start(port);
	}

	/**
	 * 
	 */
	public void start(final int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			log("[SERVER] Server started and listening on port " + port);
			while (true) { // TODO Change for a word that terminates the connection.
				try (Socket socket = serverSocket.accept();
						PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
					log("[SERVER] Client connected from IP: " + socket.getInetAddress().getLocalHost());

					output.println("[SERVER] Please provide a file for reading: ");

					String fromUser = input.readLine();
					log("[SERVER] Client sent `" + fromUser + "`.");
					// TODO treat user input. What if he sends a directory?
					String toUser = proccessInput(fromUser);
					if (toUser == null) {
						output.println("[SERVER] There was a problem reading the provided file.");
					} else {
						output.println(toUser);
					}

				} catch (IOException socketEx) {
					log("[SERVER] ");
				}
			}
		} catch (IOException serverSocketEx) {
			log("[SERVER] ");
		}
	}

	private static String proccessInput(final String fromUser) {
		try (BufferedReader fileReader = new BufferedReader(new FileReader(fromUser))) {
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = fileReader.readLine()) != null) {
				sb.append(line).append(System.lineSeparator());
			}
			return sb.toString();
		} catch (IOException e) {
			log("[SERVER] ");
		}
		return null;
	}

	/**
	 * 
	 */
	private static void log(final String message) {
		System.out.println(message);
	}

	/**
	 * 
	 */
	private static int getPort(String args) {
		try {
			return Integer.parseInt(args);
		} catch (NumberFormatException ex) {
			System.err.println("Port Number must be an integer.");
			System.exit(-1);
		}
		return -1;
	}

}