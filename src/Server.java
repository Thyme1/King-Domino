import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Server {

    private static ArrayList<ClientHandler> clients=new ArrayList<>();
    private static ArrayList<Socket> clientsSockets=new ArrayList<>();
    private static ExecutorService pool=Executors.newFixedThreadPool(5);


    public static void main(String argv[]) throws IOException, InterruptedException {
        ServerSocket welcomeSocket=new ServerSocket(6666);

        while (true) {

            Socket client=welcomeSocket.accept();

            ClientHandler clientThread=new ClientHandler(client, clients);
            pool.execute(clientThread);
            clientsSockets.add(client);

            System.out.println(clients.size());
            clients.add(clientThread);
            if (clients.size() == 4){
                pool.awaitTermination(500, TimeUnit.MILLISECONDS);
                FinalClientHandler finalClientThread=new FinalClientHandler(client, clients, clientsSockets);

                pool.execute(finalClientThread);
            }



        }

    }


}
