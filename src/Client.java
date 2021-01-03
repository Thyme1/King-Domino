import java.io.*;
import java.net.*;


public class Client {
    public static void main(String argv[]) throws IOException {

        Socket clientSocket = new Socket("localhost", 6666);
        ServerConnection serverConn = new ServerConnection(clientSocket);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        new Thread(serverConn).start();

        while(true) {
            String command=keyboard.readLine();

            if (command.equals("QUIT")) break;
            out.println(command);



        }
        clientSocket.close();
        System.exit(0);
    }
}