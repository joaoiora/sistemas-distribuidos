import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UDPClient {
    
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(30000);
            byte[] data = new byte[4];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);

            int len = 0;
            for (int i = 0; i < 4; ++i) {
                len |= (data[3 - i] & 0xff) << (i << 3);
            }

            byte[] buffer = new byte[len];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(bais);
            FileObject fileObject = (FileObject) ois.readObject();

            Path path = Paths.get(fileObject.getSourceFolder());
            Files.write(path, fileObject.getFileContent());

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }


    }

}