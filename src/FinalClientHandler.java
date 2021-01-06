import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class FinalClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in1;
    private PrintWriter out1;
    private BufferedReader in2;
    private PrintWriter out2;
    private BufferedReader in3;
    private PrintWriter out3;
    private BufferedReader in4;
    private PrintWriter out4;
    private ArrayList<ClientHandler> clients;
    private ArrayList<Socket> clientsSocket;


    public FinalClientHandler(Socket client, ArrayList<ClientHandler> clients, ArrayList<Socket> clientsSocket) throws IOException {
        this.client=client;
        this.clients=clients;
        this.clientsSocket = clientsSocket;
        in1=new BufferedReader(new InputStreamReader(clientsSocket.get(0).getInputStream()));
        out1=new PrintWriter(clientsSocket.get(0).getOutputStream(), true);

        in2=new BufferedReader(new InputStreamReader(clientsSocket.get(1).getInputStream()));
        out2=new PrintWriter(clientsSocket.get(1).getOutputStream(), true);

        in3=new BufferedReader(new InputStreamReader(clientsSocket.get(2).getInputStream()));
        out3=new PrintWriter(clientsSocket.get(2).getOutputStream(), true);

        in4=new BufferedReader(new InputStreamReader(clientsSocket.get(3).getInputStream()));
        out4=new PrintWriter(clientsSocket.get(3).getOutputStream(), true);

    }

    @Override
    public void run() {

        try {
            int errorCounter=0;
            String clientSentence1=in1.readLine();
            if (!clientSentence1.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                out1.println("ERROR here");
                errorCounter++;
            } else out1.println("OK");
            String clientSentence2=in2.readLine();
            if (!clientSentence2.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                out2.println("ERROR here");
                errorCounter++;
            } else out2.println("OK");
            String clientSentence3=in3.readLine();
            if (!clientSentence3.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                out3.println("ERROR here");
                errorCounter++;
            } else out3.println("OK");
            String clientSentence4=in4.readLine();
            if (!clientSentence4.matches("LOGIN " + "[a-zA-Z0-9]*")) {
                out4.println("ERROR here");
                errorCounter++;
            } else out4.println("OK");

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
            dominos = addDominos(dominos);
            ArrayList<String> domToWrite;
            domToWrite = pickDominos(dominos, random);
            dominos = removePickedDominos(dominos, domToWrite);
            ArrayList<String> domPickedLastRound = new ArrayList<>();







            int index=1;


            out1.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
            index++;
            out2.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
            index++;
            out3.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
            index++;
            out4.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));


            ClientHandler clientOne=clients.get(firstInt - 1);
            ClientHandler clientTwo=clients.get(secondInt - 1);
            ClientHandler clientThree=clients.get(thirdInt - 1);
            ClientHandler clientFour=clients.get(fourthInt - 1);

            clientOne.out.println("YOUR CHOICE");
            String secSentence=null;
            try {
                secSentence=clientOne.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWrite.get(0)) || secSentence.equals("CHOOSE " + domToWrite.get(1)) || secSentence.equals("CHOOSE " + domToWrite.get(2)) || secSentence.equals("CHOOSE " + domToWrite.get(3)) ) { //CHANGE LATER TO MATCH ONLY DOMINOS
                clientOne.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
            } else {
                clientOne.out.println("ERROR " + secSentence);
                errorCounter++;
            }

            clientTwo.out.println("YOUR CHOICE");
            try {
                secSentence=clientTwo.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWrite.get(0)) || secSentence.equals("CHOOSE " + domToWrite.get(1)) || secSentence.equals("CHOOSE " + domToWrite.get(2)) ) {
                clientTwo.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino);

            } else {
                clientTwo.out.println("ERROR" + secSentence);
                errorCounter++;

            }

            clientThree.out.println("YOUR CHOICE");
            try {
                secSentence=clientThree.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWrite.get(0)) || secSentence.equals("CHOOSE " + domToWrite.get(1))) {
                clientThree.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino);

            } else {


                clientThree.out.println("ERROR" + secSentence);
                errorCounter++;
            }

            clientFour.out.println("YOUR CHOICE");
            try {
                secSentence=clientFour.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWrite.get(0))) {
                clientFour.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);

            } else {

                clientFour.out.println("ERROR" + secSentence);
                errorCounter++;
            }


            domToWrite = pickDominos(dominos, random);
            dominos = removePickedDominos(dominos, domToWrite);

            out1.println("ROUND "+ domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
            out2.println("ROUND "+ domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
            out3.println("ROUND "+ domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
            out4.println("ROUND "+ domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));






        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out1.close();
            out2.close();
            out3.close();
            out4.close();
            try {
                in1.close();
                in2.close();
                in3.close();
                in4.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<String> removePickedDominos(ArrayList<String> dominos, ArrayList<String> domToWrite) {
        for(int i=0;i<4;i++){
            dominos.remove(domToWrite.get(i));
        }
        return dominos;
    }

    private ArrayList<String> addDominos(ArrayList<String> dominos) {
        for (int i=1; i < 49; i++) {
            dominos.add(String.valueOf(i));
        }
        return dominos;
    }

    private ArrayList<String> pickDominos(ArrayList<String> dominos, Random random){
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
        return domToWrite;
    }


}