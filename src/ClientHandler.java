import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class ClientHandler implements Runnable {
    public BufferedReader in;
    private BufferedReader in2;
    public PrintWriter out;
    public PrintWriter printWriter;
    public PrintWriter printWriter2;


    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out=new PrintWriter(clientSocket.getOutputStream(), true);
        File file=new File("app.log");
        File loginsFile=new File("logins.txt");
        FileWriter fileWriter=new FileWriter(file, true);
        FileWriter fileWriter2=new FileWriter(loginsFile, true);
        printWriter=new PrintWriter(fileWriter);
        printWriter2=new PrintWriter(fileWriter2);


    }

    @Override
    public void run() {

        out.println("CONNECT");
        printWriter.println("CONNECT");
        try {
            login(in, out, printWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }


        printWriter.close();



    }

    private void login(BufferedReader in1, PrintWriter out1, PrintWriter printWriter) throws IOException {
        String clientSentence1=in1.readLine();
        printWriter.println(clientSentence1);
        if (!clientSentence1.matches("LOGIN " + "[a-zA-Z0-9]*")) {
            out1.println("ERROR");
            printWriter.println("ERROR");
            login(in1, out1, printWriter);
        } else {
            printWriter.println("OK");
            out1.println("OK");
            String[] str=clientSentence1.split(" ");
            printWriter2.println(str[1]);
            printWriter2.close();
        }


    }
}