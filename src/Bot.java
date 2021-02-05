import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class Bot {
    public static void bot(Socket clientSocket) throws IOException {
        String sentenceFromServer;
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Random r = new Random();
        char clientLogin = (char)(r.nextInt(26) + 'a');
        String login = "LOGIN " + clientLogin;






        outToServer.writeBytes(login + '\n');  //to przesylam do serwera LOGIN A
        sentenceFromServer=inFromServer.readLine(); //read ok
        System.out.println(sentenceFromServer + " to to");


        sentenceFromServer=inFromServer.readLine(); //read start
        System.out.println(sentenceFromServer + "start");

        String[] array1=sentenceFromServer.split(" ");
        int clientNumber=Integer.parseInt(array1[1]);
        String choosedDomino=array1[8 - clientNumber + 1];
        if (sentenceFromServer.equals("YOUR CHOICE" + '\n')){
            outToServer.writeBytes("CHOOSE " + choosedDomino + '\n');
        }

        for (int i=0;i<11;i++) {

            if (sentenceFromServer.equals("YOUR MOVE" + '\n')){
                outToServer.writeBytes("MOVE " + 0 + (i+2) + 90 + '\n');
            }


        }
    }
}
