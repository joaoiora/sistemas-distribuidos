import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

public class UDPServer {
    
    public static void main(String[] args) {
        DatagramSocket datagramSocket = new DatagramSocket(30000);
        Path path = Paths.get("/etc/services");
        byte[] data = Files.readAllLines(path);
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        datagramSocket.send(datagramPacket);

    }


}