import org.apache.log4j.Logger;

import java.io.*;
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




    }
}
