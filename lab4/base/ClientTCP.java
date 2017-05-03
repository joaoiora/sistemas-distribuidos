import java.net.*;
import java.io.*;

public class ClientTCP {
    public static void main (String args[]){
        Socket s = null;
        try{
            int serverPort = 7896;
            s = new Socket(args[1], serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            out.writeUTF(args[0]);
            Long horaservidor = in.readLong();

            System.out.println("Hora do servidor: "+horaservidor);

        }catch(UnknownHostException e){
            System.out.println("Socket:"+e.getMessage());
        } catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch (IOException e){System.out.println("IO:"+e.getMessage());
        } finally {if(s!=null) try{s.close();} catch (IOException e){/*close falhou*/}
        }
    }
}
