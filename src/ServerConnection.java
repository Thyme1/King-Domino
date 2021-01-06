import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private Socket server;
    private BufferedReader in;
    final static Logger logger=Logger.getLogger(Server.class);


    public ServerConnection(Socket s) throws IOException {
        server=s;
        in=new BufferedReader(new InputStreamReader(server.getInputStream()));


    }

    @Override //responsible for handling inputs from the server
    public void run() {


        String serverResponse=null;
        try {
            while (true) {
                serverResponse=in.readLine();
                logger.info("SERVER: " + serverResponse);
                if (serverResponse == null) break;
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
