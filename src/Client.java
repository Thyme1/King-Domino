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
        String connect = inFromServer.readLine();
        System.out.println(connect);
        String ok = inFromServer.readLine();
        System.out.println(ok);
        String start=inFromServer.readLine();
        System.out.println(start + "start?");


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
        System.out.println(sentence + " last"); //player4/ok

        String round=inFromServer.readLine(); //round
        String[] splittedRound=round.split(" ");

//###############################################################
        int y=1;
       for(int i =0;i<11;i++) {
//           System.out.println(i + " - iteracja i");

           for (int k=0; k < 8; k++) {
//               System.out.println(i + " - iteracja i w k");
//               System.out.println(k + " - iteracja k");

               sentence=inFromServer.readLine();
               System.out.println(sentence);

               if (sentence.equals("YOUR MOVE")) {
                   out.println("MOVE " + "0 " + y + " 90");
                   sentence=inFromServer.readLine();
                   System.out.println(sentence);
                   y+=2;
               }
               if (sentence.equals("YOUR CHOICE")) {
                   out.println("CHOOSE " + splittedRound[clientNumber]);
                   sentence=inFromServer.readLine();
                   System.out.println(sentence);
               }

           }

           if(i!=10){

            round=inFromServer.readLine(); //round
           System.out.println(round + " round" + i);
           splittedRound=round.split(" ");

       }}

        for (int h=0; h < 5; h++) {
            sentence=inFromServer.readLine();
            System.out.println(sentence);

            if (sentence.equals("YOUR MOVE")) {
                out.println("MOVE " + "0 " + y + " 90");
                sentence=inFromServer.readLine();
                System.out.println(sentence);

            }

        }
        sentence=inFromServer.readLine();
        System.out.println(sentence);










        new Thread(serverConn).start();

        while (x > 0) {


        }
        clientSocket.close();
        System.exit(0);
    }
}