import java.io.*;
import java.net.*;


public class Client {
    public static void main(String argv[]) throws IOException {

        Socket clientSocket = new Socket("localhost", 6666);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        while(true) {
            String command=keyboard.readLine();

            if (command.equals("QUIT")) break;

            String serverResponse=inFromUser.readLine();
            System.out.println(serverResponse);

        }
        clientSocket.close();
        System.exit(0);
    }
}