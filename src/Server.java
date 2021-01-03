import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ArrayList<ClientHandler> clients=new ArrayList<>();
    private static ExecutorService pool=Executors.newFixedThreadPool(4);

    public static void main(String argv[]) throws IOException {
        ServerSocket welcomeSocket=new ServerSocket(6666);

        while (true) {
            Socket client=welcomeSocket.accept();
            System.out.println("CONNECT");
            ClientHandler clientThread=new ClientHandler(client);
            clients.add(clientThread);

            pool.execute(clientThread);
        }
    }


}
