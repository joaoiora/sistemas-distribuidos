package br.ucb.joaoiora.threads;

import br.ucb.joaoiora.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class ClientHandlerThread implements Runnable {

    private Socket socket;
    private PrintWriter serverOutput;
    private BufferedReader serverInput;

    public ClientHandlerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        this.serverOutput = new PrintWriter(socket.getOutputStream()); // change to ObjectOutputStream
        this.serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Change to ObjectInputStream
        String fromClient = "";
        serverOutput.println("[SERVER] Please enter a directory: ");
        fromClient = serverInput.readLine();
        List<String> files = getFilesInDirectory(fromClient);
        if (files == null) {
            serverOutput.println("[SERVER] Invalid Directory");
        } else {
            for (String filename : files) {
                serverOutput.println(filename);
            }
            serverOutput.println("[SERVER] Please enter the name of file you want transferred: ");
        }
        fromClient = serverInput.readLine();
        // TODO check if response from client is an actual file
        // TODO from here, create DatagramSocket to transfer file
    }

    private String proccessClientInput() {
        String string = serverInput.readLine();
        if (StringUtils.isEmpty(string)) {

        }
        if (string.equals("quit")) {
            socket.close();
        }
        return string;
    }

    private static List<String> getFilesInDirectory(final String rootPath) {
        File file = new File(rootPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                return getFilenames(file);
            }
        }
        return null;
    }

    private static List<String> getFilenames(File rootPath) {
        List<String> filenames = new ArrayList<>();
        File[] files = rootPath.listFiles();
        for (File file : files) {
            filenames.add(file.getName());
        }
        return filenames;
    }

}
