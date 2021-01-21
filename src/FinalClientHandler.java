import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
    int[] errorCounter = {0,0,0,0};




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


            login(in1, out1, first);
            login(in2, out2, second);
            login(in3, out3, third);
            login(in4, out4, fourth);



            String x;
            String y;
            String orientation;
            ArrayList<String> move;
            ArrayList<Integer> domPickedLastRoundInt = new ArrayList<>();
            ArrayList<Integer> domToWriteInt = new ArrayList<>();

            ArrayList<String> dominos=new ArrayList<>();
            dominos=addDominos(dominos);
            ArrayList<String> domToWrite;
            domToWrite=pickDominos(dominos, random);
            dominos=removePickedDominos(dominos, domToWrite);
            ArrayList<String> domPickedLastRound=new ArrayList<>();
            ArrayList<ClientHandler> clientsChange = new ArrayList<>();

            for(int i=0; i<domToWrite.size();i++){
                domToWriteInt.add(Integer.valueOf(domToWrite.get(i)));
            }
            Collections.sort(domToWriteInt);


            int index=1;


            index=startPrint(first, second, third, fourth, domToWriteInt, index, out1, out2);
            index++;
            index=startPrint(first, second, third, fourth, domToWriteInt, index, out3, out4);


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

            if (secSentence.equals("CHOOSE " + domToWriteInt.get(0)) || secSentence.equals("CHOOSE " + domToWriteInt.get(1)) || secSentence.equals("CHOOSE " + domToWriteInt.get(2)) || secSentence.equals("CHOOSE " + domToWriteInt.get(3))) { //CHANGE LATER TO MATCH ONLY DOMINOS
                clientOne.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                System.out.println(domToWrite);
                System.out.println(domToWriteInt);
                domToWrite.remove(chosenDomino);
                domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));

                clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
            } else {
                clientOne.out.println("ERROR " + secSentence);
                errorCounter[0] += 1;
            }

            clientTwo.out.println("YOUR CHOICE");
            try {
                secSentence=clientTwo.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWriteInt.get(0)) || secSentence.equals("CHOOSE " + domToWriteInt.get(1)) || secSentence.equals("CHOOSE " + domToWriteInt.get(2))) {
                clientTwo.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                System.out.println(domToWrite);
                System.out.println(domToWriteInt);
                domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
                domToWrite.remove(chosenDomino);

                clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino);

            } else {
                clientTwo.out.println("ERROR" + secSentence);
                errorCounter[1]+=1;

            }

            clientThree.out.println("YOUR CHOICE");
            try {
                secSentence=clientThree.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWriteInt.get(0)) || secSentence.equals("CHOOSE " + domToWriteInt.get(1))) {
                clientThree.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                System.out.println(domToWrite);
                System.out.println(domToWriteInt);
                domToWrite.remove(chosenDomino);
                domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
                clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino);

            } else {


                clientThree.out.println("ERROR " + secSentence);
                errorCounter[2]+=1;

            }

            clientFour.out.println("YOUR CHOICE");
            try {
                secSentence=clientFour.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (secSentence.equals("CHOOSE " + domToWriteInt.get(0))) {
                clientFour.out.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                domPickedLastRound.add(chosenDomino);
                System.out.println(domToWrite);
                System.out.println(domToWriteInt);
                domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);

            } else {

                clientFour.out.println("ERROR" + secSentence);
                errorCounter[3]+=1;
            }

            ArrayList<String> order = new ArrayList<>();


            String[][] board1 = new String[201][201];
            String[][] board2 = new String[201][201];
            String[][] board3 = new String[201][201];
            String[][] board4 = new String[201][201];

            board1 = fillArrayWithZeos(board1);
            board2 = fillArrayWithZeos(board2);
            board3 = fillArrayWithZeos(board3);
            board4 = fillArrayWithZeos(board4);

            board1[100][100] = "X";
            board2[100][100] = "X";
            board3[100][100] = "X";
            board4[100][100] = "X";

            ArrayList<String[][]> boards = new ArrayList();
            boards.add(board1);
            boards.add(board2);
            boards.add(board3);
            boards.add(board4);

//            for (int row = 0; row < board1.length; row++)//Cycles through rows
//            {
//                for (int col = 0; col < board1[row].length; col++)//Cycles through columns
//                {
//                    System.out.printf(board1[row][col]); //change the %5d to however much space you want
//                }
//                System.out.println(); //Makes a new row
//            }

//            String[][] playerBoard = new String[4][2];
//            playerBoard[0][0] = first;
//            playerBoard[1][0] = second;
//            playerBoard[2][0] = third;
//            playerBoard[3][0] = fourth;
//            playerBoard[0][1] = board1;
//            playerBoard[1][2] = board2;
//            playerBoard[2][3] = board3;
//            playerBoard[3][4] = board4;







            for (int i=0; i < 10; i++) {

                domToWrite=pickDominos(dominos, random);
                for(int j=0; j<domToWrite.size();j++){
                    domToWriteInt.add(Integer.valueOf(domToWrite.get(j)));
                }
                Collections.sort(domToWriteInt);

                dominos=removePickedDominos(dominos, domToWrite);
                System.out.println(domPickedLastRound);

                order.add(first);
                order.add(second);
                order.add(third);
                order.add(fourth);



                for(i=0;i<4;i++){
                    domPickedLastRoundInt.add(Integer.parseInt(domPickedLastRound.get(i)));
                }
                for(i=0;i<4;i++){
                    domPickedLastRound.remove(0);
                }

                out1.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                out2.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                out3.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                out4.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));

                System.out.println("PRZED: ");
                System.out.println(domPickedLastRoundInt);
                firstInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(firstInt-1, 50);
                System.out.println(domPickedLastRoundInt);


                secondInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(secondInt-1, 50);
                System.out.println(domPickedLastRoundInt);


                thirdInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(thirdInt-1, 50);
                System.out.println(domPickedLastRoundInt);


                fourthInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1 ;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt)) ;
                domPickedLastRoundInt.add(fourthInt-1, 50);
                System.out.println(domPickedLastRoundInt);


                clientsChange.add(clientOne);
                clientsChange.add(clientTwo);
                clientsChange.add(clientThree);
                clientsChange.add(clientFour);


                clientOne=clientsChange.get(firstInt - 1);
                clientTwo=clientsChange.get(secondInt - 1);
                clientThree=clientsChange.get(thirdInt - 1);
                clientFour=clientsChange.get(fourthInt- 1);

                first = order.get(firstInt - 1);
                second = order.get(secondInt - 1);
                third = order.get(thirdInt - 1);
                fourth = order.get(fourthInt - 1);

                //test
                clientOne.out.println("siema jestes teraz 1 w kolejce");
                clientTwo.out.println("siema jestes teraz 2 w kolejce");
                clientThree.out.println("siema 3 w kolejce");
                clientFour.out.println("siema 4 w kolejce");

                System.out.println("first sec itp");
                System.out.println(first);
                System.out.println(second);
                System.out.println(third);
                System.out.println(fourth);

                System.out.println("firstInt secInt itp");
                System.out.println(firstInt);
                System.out.println(secondInt);
                System.out.println(thirdInt);
                System.out.println(fourthInt);


                move=yourMove(clientOne, boards.get(Integer.parseInt(first)-1), first );
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientTwo.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                String chosenDomino=yourChoice(clientOne, domToWrite, first);
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino);


                move=yourMove(clientTwo, boards.get(Integer.parseInt(second) - 1), third);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientOne.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                chosenDomino=yourChoice(clientTwo, domToWrite, second);
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino);

                move=yourMove(clientThree, boards.get(Integer.parseInt(third) - 1), third);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientTwo.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientOne.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                chosenDomino=yourChoice(clientThree, domToWrite,third);
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino);

                move=yourMove(clientFour, boards.get(Integer.parseInt(fourth) - 1),fourth);
                x=move.get(0);
                y=move.get(1);
                orientation=move.get(2);
                clientTwo.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientOne.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + x + " " + y + " " + orientation);

                chosenDomino=yourChoice(clientFour, domToWrite,fourth);
                domPickedLastRound.add(chosenDomino);
                domToWrite.remove(chosenDomino);
                clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);

                for(i=0;i<4;i++){
                    domPickedLastRoundInt.remove(0);
                    clientsChange.remove(0);
                    order.remove(0);
                }
                for(i=0;i<4;i++){
                    domToWriteInt.remove(0);
                }



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

    private String[][] fillArrayWithZeos(String[][] arr) {
        Arrays.stream(arr).forEach(a -> Arrays.fill(a, "0"));
        return arr;
    }

    private void login(BufferedReader in1, PrintWriter out1, String clientNumber) throws IOException {
        String clientSentence1=in1.readLine();
        if (!clientSentence1.matches("LOGIN " + "[a-zA-Z0-9]*")) {
            out1.println("ERROR here");
            errorCounter[Integer.parseInt(clientNumber)-1]+=1;
        } else out1.println("OK");

    }

    private int startPrint(String first, String second, String third, String fourth, ArrayList<Integer> domToWriteInt, int index, PrintWriter out1, PrintWriter out2) {
        out1.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
        index++;
        out2.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
        return index;
    }

    private String yourChoice(ClientHandler clientOne, ArrayList<String> domToWrite, String clientNumber) {
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
            errorCounter[Integer.parseInt(clientNumber)-1]+=1;;
            return secSentence;
        }
    }

    private ArrayList<String> yourMove(ClientHandler clientOne, String[][] board, String clientNumber) {
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
            if (catchMoveMistake(x_coorInt, y_coorInt, orientationInt, board, clientNumber)){
                errorCounter[Integer.parseInt(clientNumber)-1]+=1;
                return yourMove(clientOne, board, clientNumber);
            }
            else if (secSentence.equals("MOVE " + x_coor + " " + y_coor + " " + orientation)) {
                clientOne.out.println("OK");
                result.add(x_coor);
                result.add(y_coor);
                result.add(orientation);
                return result;


            } else {
                clientOne.out.println("ERROR " + secSentence);
                errorCounter[Integer.parseInt(clientNumber)-1]+=1;
                return yourMove(clientOne, board, clientNumber);
            }

        } else {
            clientOne.out.println("ERROR " + secSentence);
            errorCounter[Integer.parseInt(clientNumber)-1]+=1;
            return yourMove(clientOne, board, clientNumber);
        }


    }

    private boolean catchMoveMistake(int x, int y, int orientation, String[][] board, String clientNumber) {

        if(!board[x][y].equals("0")){
            return true;}
        else if (orientation == 0){
            if(!board[x][y+1].equals("0")){
                return true; }
            else{
                return !(board[x - 1][y].equals("0") && board[x + 2][y].equals("0") && board[x][y - 1].equals("0") && board[x][y + 1].equals("0") && board[x + 1][y - 1].equals("0") && board[x + 1][y + 1].equals("0"));
            }
        }
        else if (orientation == 90){
            if(!board[x+1][y].equals("0")){
                return true; }
            else{
                return !(board[x - 1][y].equals("0") && board[x][y-1].equals("0") && board[x][y+1].equals("0") && board[x+1][y + 1].equals("0") && board[x + 1][y - 1].equals("0") && board[x+2][y].equals("0"));
            }
        }
        else if (orientation == 180){
            if(!board[x][y-1].equals("0")){
                return true; }
            else{
                return !(board[x][y+1].equals("0") && board[x-1][y].equals("0") && board[x+1][y].equals("0") && board[x+1][y + 1].equals("0") && board[x - 1][y - 1].equals("0") && board[x][y-2].equals("0"));
            }
        }
        else if (orientation == 270){
            if(!board[x-1][y].equals("0")){
                return true; }
            else{
                return !(board[x+1][y].equals("0") && board[x][y-1].equals("0") && board[x][y+1].equals("0") && board[x-1][y + 1].equals("0") && board[x - 1][y - 1].equals("0") && board[x-2][y].equals("0"));
            }
        }
        return false;
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