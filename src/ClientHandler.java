import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;


    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client=clientSocket;
        this.clients=clients;
        in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        out=new PrintWriter(client.getOutputStream(), true);


    }

    @Override
    public void run() {
        int errorCounter=0;
        ArrayList<String> numbers=new ArrayList<>();
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        Random random=new Random();
        String first=numbers.get(random.nextInt(numbers.size()));
        numbers.remove(first);
        String second=numbers.get(random.nextInt(numbers.size()));
        numbers.remove(second);
        String third=numbers.get(random.nextInt(numbers.size()));
        numbers.remove(third);
        String fourth=numbers.get(0);
        int firstInt=Integer.parseInt(first);
        int secondInt=Integer.parseInt(second);
        int thirdInt=Integer.parseInt(third);
        int fourthInt=Integer.parseInt(fourth);

        ArrayList<String> dominos=new ArrayList<>();
        dominos=addDominos(dominos);


        try {
            out.println("CONNECT");
            while (true) {
                if (errorCounter == 100) break;
                String clientSentence=in.readLine();
                if (!clientSentence.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                    out.println("ERROR");
                    errorCounter++;
                } else out.println("OK");

                if (clients.size() == 4) {

                    int index=1;


                    String domino1=dominos.get(random.nextInt((dominos.size())));
                    dominos.remove(domino1);
                    String domino2=dominos.get(random.nextInt((dominos.size())));
                    dominos.remove(domino2);
                    String domino3=dominos.get(random.nextInt((dominos.size())));
                    dominos.remove(domino3);
                    String domino4=dominos.get(random.nextInt((dominos.size())));
                    dominos.remove(domino4);

                    ArrayList<String> domToWrite=new ArrayList<>();
                    domToWrite.add(domino1);
                    domToWrite.add(domino2);
                    domToWrite.add(domino3);
                    domToWrite.add(domino4);
                    Collections.sort(domToWrite);



                    for (ClientHandler aClient : clients) {
                        aClient.out.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
                        index++;
                    }

                    ClientHandler clientOne = clients.get(firstInt-1);
                    ClientHandler clientTwo = clients.get(secondInt-1);
                    ClientHandler clientThree = clients.get(thirdInt-1);
                    ClientHandler clientFour = clients.get(fourthInt-1);


                    clientOne.out.println("YOUR CHOICE");
                    String secSentence=in.readLine();
                    System.out.println(secSentence);
                    if (!secSentence.matches("CHOOSE " + "8")) { //CHANGE LATER TO MATCH ONLY DOMINOS
                        out.println("ERROR");
                        errorCounter++;
                    } else{
                        out.println("OK");
                        String chosenDomino = secSentence.split(" ")[1];
                        clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino );
                        clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino );
                        clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino );

                    }

                    clientTwo.out.println("YOUR CHOICE");
                    String thirdSentence=in.readLine();
                    System.out.println(thirdSentence);
                    if (!thirdSentence.matches("CHOOSE " + "8")) { //CHANGE LATER TO MATCH ONLY DOMINOS - one taken
                        out.println("ERROR");
                        errorCounter++;
                    } else{
                        out.println("OK");
                        String chosenDomino = thirdSentence.split( " ")[1];
                        clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino );
                        clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino );
                        clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino );
                    }

                    clientThree.out.println("YOUR CHOICE");
                    String fourthSentence=in.readLine();
                    System.out.println(fourthSentence);
                    if (!fourthSentence.matches("CHOOSE " + "8")) { //CHANGE LATER TO MATCH ONLY DOMINOS - one taken
                        out.println("ERROR");
                        errorCounter++;
                    } else{
                        out.println("OK");
                        String chosenDomino = fourthSentence.split(" ")[1];
                        clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino );
                        clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino );
                        clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino );
                    }

                    clientTwo.out.println("YOUR CHOICE");
                    String fifthSentence=in.readLine();
                    if (!fifthSentence.matches("CHOOSE " + "8")) { //CHANGE LATER TO MATCH ONLY DOMINOS - one taken
                        out.println("ERROR");
                        errorCounter++;
                    } else{
                        out.println("OK");
                        String chosenDomino = fifthSentence.split(" ")[1];
                        clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino );
                        clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino );
                        clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino );
                    }






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

    private ArrayList<String> addDominos(ArrayList<String> dominos) {
        for (int i=1; i < 49; i++) {
            dominos.add(String.valueOf(i));
        }
        return dominos;
    }


    private void outToAll(String msg) {
        for (ClientHandler aClient : clients) {
            aClient.out.println(msg);
        }
    }

}

