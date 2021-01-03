import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;



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
        ArrayList<String> numbers = new ArrayList<>();
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        Random random = new Random();
        String first = numbers.get(random.nextInt(numbers.size()));
        numbers.remove(first);
        String second = numbers.get(random.nextInt(numbers.size()));
        numbers.remove(second);
        String third = numbers.get(random.nextInt(numbers.size()));
        numbers.remove(third);
        String fourth = numbers.get(0);
        try {
            while (true) {
                if (errorCounter == 100) break;
                String clientSentence=in.readLine();
                if (!clientSentence.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                    out.println("ERROR");
                    errorCounter++;
                } else out.println("OK");

                if (clients.size() == 4){
                    out.println("START "+ first+" "+ first+" "+second+" "+third+" "+fourth );


                }
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

    private void outToAll(String msg){
        for (ClientHandler aClient : clients){
            aClient.out.println(msg);
        }
    }
}
