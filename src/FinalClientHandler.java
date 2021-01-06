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

    static int errorCounter=0;

    public FinalClientHandler(Socket client, ArrayList<ClientHandler> clients, ArrayList<Socket> clientsSocket) throws IOException {
        this.client=client;
        this.clients=clients;
        this.clientsSocket=clientsSocket;
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
            String x;
            String y;
            String orientation;
            ArrayList<String> move=new ArrayList<>();
            ArrayList<Integer> domPickedLastRoundInt = new ArrayList<>();

            ArrayList<String> dominos=new ArrayList<>();
            dominos=addDominos(dominos);
            ArrayList<String> domToWrite;
            domToWrite=pickDominos(dominos, random);
            dominos=removePickedDominos(dominos, domToWrite);
            ArrayList<String> domPickedLastRound=new ArrayList<>();
            ArrayList<ClientHandler> clientsChange = new ArrayList<>();
            Collections.sort(domToWrite);


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

            if (secSentence.equals("CHOOSE " + domToWrite.get(0)) || secSentence.equals("CHOOSE " + domToWrite.get(1)) || secSentence.equals("CHOOSE " + domToWrite.get(2)) || secSentence.equals("CHOOSE " + domToWrite.get(3))) { //CHANGE LATER TO MATCH ONLY DOMINOS
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

            if (secSentence.equals("CHOOSE " + domToWrite.get(0)) || secSentence.equals("CHOOSE " + domToWrite.get(1)) || secSentence.equals("CHOOSE " + domToWrite.get(2))) {
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


            for (int i=0; i < 10; i++) {

                domToWrite=pickDominos(dominos, random);
                Collections.sort(domToWrite);
                dominos=removePickedDominos(dominos, domToWrite);
                System.out.println(domPickedLastRound);

                domPickedLastRoundInt = null;

                for(i=0;i<3;i++){
                    domPickedLastRoundInt.add(Integer.parseInt(domPickedLastRound.get(i)));
                }

                out1.println("ROUND " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
                out2.println("ROUND " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
                out3.println("ROUND " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));
                out4.println("ROUND " + domToWrite.get(0) + " " + domToWrite.get(1) + " " + domToWrite.get(2) + " " + domToWrite.get(3));

                firstInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(firstInt-1, 50);

                secondInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(secondInt-1, 50);

                thirdInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(thirdInt-1, 50);

                fourthInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1 ;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt)) ;
                domPickedLastRoundInt.add(fourthInt-1, 50);

                clientsChange.add(clientOne);
                clientsChange.add(clientTwo);
                clientsChange.add(clientThree);
                clientsChange.add(clientFour);


                clientOne=clientsChange.get(firstInt - 1);
                clientTwo=clientsChange.get(secondInt - 1);
                clientThree=clientsChange.get(thirdInt - 1);
                clientFour=clientsChange.get(fourthInt- 1);


                move=YourMove(clientOne);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientTwo.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                String chosenDomino=YourChoice(clientOne, domToWrite);
                domToWrite.remove(chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino);


                move=YourMove(clientTwo);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientOne.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                chosenDomino=YourChoice(clientTwo, domToWrite);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino);

                move=YourMove(clientThree);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientTwo.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientOne.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                chosenDomino=YourChoice(clientThree, domToWrite);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino);

                move=YourMove(clientFour);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientTwo.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientOne.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                chosenDomino=YourChoice(clientFour, domToWrite);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);

            }

            out1.println("ROUND");
            out2.println("ROUND");
            out3.println("ROUND");
            out4.println("ROUND");


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

    private String YourChoice(ClientHandler clientOne, ArrayList<String> domToWrite) {
        clientOne.out.println("YOUR CHOICE");
        String secSentence=null;
        try {
            secSentence=clientOne.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (secSentence.equals("CHOOSE " + domToWrite.get(0)) || secSentence.equals("CHOOSE " + domToWrite.get(1)) || secSentence.equals("CHOOSE " + domToWrite.get(2)) || secSentence.equals("CHOOSE " + domToWrite.get(3))) { //CHANGE LATER TO MATCH ONLY DOMINOS
            clientOne.out.println("OK");
            String chosenDomino=secSentence.split(" ")[1];
            return chosenDomino;
        } else {
            clientOne.out.println("ERROR " + secSentence);
            errorCounter++;
            return secSentence;
        }
    }

    private ArrayList<String> YourMove(ClientHandler clientOne) {
        ArrayList<String> result=new ArrayList<>();
        clientOne.out.println("YOUR MOVE");
        String secSentence=null;
        try {
            secSentence=clientOne.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String x_coor=secSentence.split(" ")[1];
        String y_coor=secSentence.split(" ")[2];
        String orientation=secSentence.split(" ")[3];

        int x_coorInt=Integer.parseInt(x_coor);
        int y_coorInt=Integer.parseInt(y_coor);
        int orientationInt=Integer.parseInt(orientation);

        if (x_coorInt >= -100 && x_coorInt <= 100 && y_coorInt >= -100 && y_coorInt <= 100 && (orientationInt == 0 || orientationInt == 90 || orientationInt == 180 || orientationInt == 270)) {
            if (secSentence.equals("MOVE " + x_coor + " " + y_coor + " " + orientation)) {
                clientOne.out.println("OK");
                result.add(x_coor);
                result.add(y_coor);
                result.add(orientation);
                return result;


            } else {
                clientOne.out.println("ERROR " + secSentence);
                errorCounter++;
                return result;
            }

        } else {
            clientOne.out.println("ERROR " + secSentence);
            errorCounter++;
            return result;
        }


    }

    private ArrayList<String> removePickedDominos(ArrayList<String> dominos, ArrayList<String> domToWrite) {
        for (int i=0; i < 4; i++) {
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

    private ArrayList<String> pickDominos(ArrayList<String> dominos, Random random) {
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