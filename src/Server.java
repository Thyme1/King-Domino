import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

public class Server {

    private static ArrayList<ClientHandler> clients=new ArrayList<>();
    private static ExecutorService pool=Executors.newFixedThreadPool(4);
    final static Logger logger = Logger.getLogger(Server.class);

    public static void main(String argv[]) throws IOException {
        ServerSocket welcomeSocket=new ServerSocket(6666);

        while (true) {
            Socket client=welcomeSocket.accept();
            System.out.println("CONNECT");
            ClientHandler clientThread=new ClientHandler(client, clients);
            clients.add(clientThread);

            pool.execute(clientThread);
        }
    }


}
