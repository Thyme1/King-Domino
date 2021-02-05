import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ClientHandler implements Runnable {
    private Socket client;
    public BufferedReader in;
    public PrintWriter out;
    private ArrayList<ClientHandler> clients;




    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client=clientSocket;
        this.clients=clients;
        in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        out=new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {

        out.println("CONNECT");
        out.println("Test");


    }


}