import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.InetAddress;

public class UDPServer {
    
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\Joao\\Desktop\\sv_fair.txt");
            byte[] fileContent = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            FileObject fileObject = new FileObject("myFileObjectName", "C:\\Users\\Joao\\Desktop\\sv_fair.txt", "C:\\Windows\\Temp");
            fileObject.setFileContent(fileContent);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(fileObject);
            oos.flush();
            byte[] buffer = baos.toByteArray();
            int number = buffer.length;
            byte[] data = new byte[4];
            for (int i = 0; i < 4; i++) {
                int shift = i << 3; // i * 8
                data[3 - i] = (byte) ((number & (0xff << shift)) >>> shift);
            }

            DatagramSocket datagramSocket = new DatagramSocket(30000);
            DatagramPacket datagramPacket = new DatagramPacket(data, 4, InetAddress.getByName("localhost"), 30000);
            datagramSocket.send(datagramPacket);

            datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 30000);
            datagramSocket.send(datagramPacket);
            System.out.println("File sending is over...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}