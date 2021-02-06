import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.Random;


public class Client {

    public static void main(String argv[]) throws IOException {


        Socket clientSocket=new Socket("localhost", 6666);
        ServerConnection serverConn=new ServerConnection(clientSocket);
        int x=2;
        String serverResponse;

        String abc="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r=new Random();
        char login=abc.charAt(r.nextInt(abc.length()));
        String sentence="LOGIN " + login;

        InputStream targetStream=new ByteArrayInputStream(sentence.getBytes());
        BufferedReader in=new BufferedReader(new InputStreamReader(targetStream));
        PrintWriter out=new PrintWriter(clientSocket.getOutputStream(), true); //to jest to co klient przekazuje do serwera


        BufferedReader inFromServer=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        out.println(sentence);//LOGIN A
        inFromServer.readLine();
        inFromServer.readLine();
        String start=inFromServer.readLine();
        System.out.println(start);

        String[] str=start.split(" ");
        int clientNumber=Integer.parseInt(str[1]);


        for (int i=0; i < 4; i++) {
            sentence=inFromServer.readLine();
            System.out.println(sentence);
            if (sentence.equals("YOUR CHOICE")) {
                out.println("CHOOSE " + str[9 - clientNumber + 1]);
            }

        }
        sentence = inFromServer.readLine(); //player4/ok
        System.out.println(sentence + " last");


        String round=inFromServer.readLine(); //round
        System.out.println(round);
        int y=1;
        String[] splittedRound=round.split(" ");
        sentence = inFromServer.readLine();
        System.out.println(sentence + " jo");

        out.println("MOVE 0 1 90");
        out.println("MOVE 0 1 90");
        out.println("MOVE 0 1 90");

        sentence = inFromServer.readLine();
        System.out.println(sentence + " SA");
        y+=2;
        out.println("CHOOSE " + splittedRound[clientNumber]);





        new Thread(serverConn).start();

        while (x > 0) {


        }
        clientSocket.close();
        System.exit(0);
    }
}