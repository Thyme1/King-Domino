import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class ClientHandler implements Runnable {
    private Socket client;
    public BufferedReader in;
    private BufferedReader in2;
    public PrintWriter out;
    public PrintWriter printWriter;
    private ArrayList<ClientHandler> clients;
    private File file;
    private Scanner scanner;




    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client=clientSocket;
        this.clients=clients;
        in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        out=new PrintWriter(client.getOutputStream(), true);
        file = new File("app.log");
        FileWriter fileWriter = new FileWriter(file, true);
        printWriter = new PrintWriter(fileWriter);



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
        } else printWriter.println("OK");
            out1.println("OK");

    }
}