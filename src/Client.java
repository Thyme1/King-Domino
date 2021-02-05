import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.Random;


public class Client {
    final static Logger logger=Logger.getLogger(Server.class);
    public static void main(String argv[]) throws IOException {

        Socket clientSocket=new Socket("localhost", 6666);
        ServerConnection serverConn=new ServerConnection(clientSocket);
        int x= 2;

        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        char login = abc.charAt(r.nextInt(abc.length()));


        String sentence= "LOGIN " + login + '\n';
        InputStream targetStream = new ByteArrayInputStream(sentence.getBytes());



        BufferedReader in=new BufferedReader(new InputStreamReader(targetStream));
        PrintWriter out=new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(sentence);

        new Thread(serverConn).start();

        while (x>0) {


        }
        clientSocket.close();
        System.exit(0);
    }
}