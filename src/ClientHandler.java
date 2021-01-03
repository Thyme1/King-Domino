import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);


    }

    @Override
    public void run() {
        int errorCounter=0;
        try {
            while (true) {
                if (errorCounter == 250) break;
                String clientSentence=in.readLine();
                System.out.println(clientSentence);
                if (!clientSentence.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                    out.println("ERROR\n");
                    errorCounter++;
                } else out.println("OK\n");
            }
        } catch (IOException e) {
            out.println("ERROR\n");
            errorCounter++;

        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
