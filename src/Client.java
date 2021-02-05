import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.Random;


public class Client {

    public static void main(String argv[]) throws IOException {

        Socket clientSocket=new Socket("localhost", 6666);
        ServerConnection serverConn=new ServerConnection(clientSocket);
        int x= 2;
        String serverResponse;

        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        char login = abc.charAt(r.nextInt(abc.length()));
        String sentence= "LOGIN " + login + '\n';

        InputStream targetStream = new ByteArrayInputStream(sentence.getBytes());
        BufferedReader in=new BufferedReader(new InputStreamReader(targetStream));
        PrintWriter out=new PrintWriter(clientSocket.getOutputStream(), true); //to jest to co klient przekazuje do serwera

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        out.println(sentence);//LOGIN A
        inFromServer.readLine();
        inFromServer.readLine();
        inFromServer.readLine();
        String start = inFromServer.readLine();

        String[] str= start.split(" ");
        int clientNumber = Integer.parseInt(str[1]);
        




        new Thread(serverConn).start();

        while (x>0) {


        }
        clientSocket.close();
        System.exit(0);
    }
}