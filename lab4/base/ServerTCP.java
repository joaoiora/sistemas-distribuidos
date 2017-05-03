import java.io.*;
import java.net.*;

public class ServerTCP {
    public static void main(String args[]){

	Socket clientSocket = null;
	ServerSocket listenSocket = null;

        try{
            int serverPort = 7896;
            listenSocket =  new ServerSocket(serverPort);
            while(true){
                clientSocket = listenSocket.accept();
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                String recebe = in.readUTF();
                out.writeLong(System.currentTimeMillis());

		clientSocket.close();
            }
    
        } catch(EOFException e) {System.out.println("EOF:"+e.getMessage()); 
       	} catch(IOException e) {System.out.println("IO:"+e.getMessage());  
	} finally {if(listenSocket!=null) try{listenSocket.close();} catch (IOException e){/*close falhou*/} }

    }
}

