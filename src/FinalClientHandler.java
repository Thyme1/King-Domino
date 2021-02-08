import java.io.*;
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
    private File file;
    private PrintWriter printWriter;
    private ArrayList<Socket> clientsSocket;
    int[] errorCounter={0, 0, 0, 0};


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
        file=new File("app.log");
        FileWriter fileWriter=new FileWriter(file, true);
        printWriter=new PrintWriter(fileWriter);


    }

    @Override
    public void run() {

        try {

            BufferedReader reader=new BufferedReader(new FileReader("logins.txt"));
            String line;
            String login1=null;
            String login2=null;
            String login3=null;
            String login4=null;


            line=reader.readLine();
            System.out.println(line);
            login1=line;
            line=reader.readLine();
            login2=line;
            line=reader.readLine();
            login3=line;
            line=reader.readLine();
            login4=line;

            File loginsFile=new File("logins.txt");
            try {
                FileWriter fileWriter2=new FileWriter(loginsFile, false);
                PrintWriter printWriter2=new PrintWriter(fileWriter2);
                printWriter2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] brick1={"s", "s"};
            String[] brick2={"s", "s"};
            String[] brick3={"f", "f"};
            String[] brick4={"f", "f"};
            String[] brick5={"f", "f"};
            String[] brick6={"f", "f"};
            String[] brick7={"w", "w"};
            String[] brick8={"w", "w"};
            String[] brick9={"w", "w"};
            String[] brick10={"g", "g"};
            String[] brick11={"g", "g"};
            String[] brick12={"b", "b"};
            String[] brick13={"s", "f"};
            String[] brick14={"s", "w"};
            String[] brick15={"s", "g"};
            String[] brick16={"s", "b"};
            String[] brick17={"f", "w"};
            String[] brick18={"f", "g"};
            String[] brick19={"ss", "f"};
            String[] brick20={"ss", "w"};
            String[] brick21={"ss", "g"};
            String[] brick22={"ss", "b"};
            String[] brick23={"ss", "m"};
            String[] brick24={"ff", "s"};
            String[] brick25={"ff", "s"};
            String[] brick26={"ff", "s"};
            String[] brick27={"ff", "s"};
            String[] brick28={"ff", "w"};
            String[] brick29={"ff", "g"};
            String[] brick30={"ww", "s"};
            String[] brick31={"ww", "s"};
            String[] brick32={"ww", "f"};
            String[] brick33={"ww", "f"};
            String[] brick34={"ww", "f"};
            String[] brick35={"ww", "f"};
            String[] brick36={"s", "gg"};
            String[] brick37={"w", "gg"};
            String[] brick38={"s", "bb"};
            String[] brick39={"g", "bb"};
            String[] brick40={"mm", "s"};
            String[] brick41={"s", "ggg"};
            String[] brick42={"w", "ggg"};
            String[] brick43={"s", "bbb"};
            String[] brick44={"g", "bbb"};
            String[] brick45={"mmm", "s"};
            String[] brick46={"b", "mmm"};
            String[] brick47={"b", "mmm"};
            String[] brick48={"s", "mmmm"};

            String[][] bricks={brick1, brick2, brick3, brick4, brick5, brick6, brick7, brick8, brick9, brick10, brick11, brick12, brick13, brick14, brick15, brick16, brick17, brick18, brick19, brick20, brick21, brick22, brick23, brick24, brick25, brick26, brick27, brick28, brick29, brick30, brick31, brick32, brick33, brick34, brick35, brick36, brick37, brick38, brick39, brick40, brick41, brick42, brick43, brick44, brick45, brick46, brick47, brick48};


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


            ArrayList<String> move;
            ArrayList<Integer> domPickedLastRoundInt=new ArrayList<>();
            ArrayList<Integer> domToWriteInt=new ArrayList<>();

            ArrayList<String> dominos=new ArrayList<>();
            dominos=addDominos(dominos);
            ArrayList<String> domToWrite;
            domToWrite=pickDominos(dominos, random);
            dominos=removePickedDominos(dominos, domToWrite);
            ArrayList<String> domPickedLastRound=new ArrayList<>();
            ArrayList<ClientHandler> clientsChange=new ArrayList<>();

            for (int i=0; i < domToWrite.size(); i++) {
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
            printWriter.println("YOUR CHOICE");
            String chosenDomino=yourChoice(clientOne, domToWrite, first);
            domPickedLastRound.add(chosenDomino);
            System.out.println(domToWrite + " domtowrite client 1 before remove");
            domToWrite.remove(chosenDomino);
            System.out.println(domToWrite + " domtowrite client 1 after remove");
            domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
            clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
            clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
            clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + first + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + first + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + first + " " + chosenDomino);


            clientTwo.out.println("YOUR CHOICE");
            printWriter.println("YOUR CHOICE");
            System.out.println(domToWrite + " domtowrite client 2 before choice");
            chosenDomino=yourChoice(clientTwo, domToWrite, second);
            domPickedLastRound.add(chosenDomino);
            domToWrite.remove(chosenDomino);
            System.out.println(domToWrite + " domtowrite client 2 after remove");
            domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
            clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
            clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
            clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + second + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + second + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + second + " " + chosenDomino);

            clientThree.out.println("YOUR CHOICE");
            printWriter.println("YOUR CHOICE");
            chosenDomino=yourChoice(clientThree, domToWrite, third);
            domPickedLastRound.add(chosenDomino);
            domToWrite.remove(chosenDomino);
            domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
            clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
            clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
            clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + third + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + third + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + third + " " + chosenDomino);

            clientFour.out.println("YOUR CHOICE");
            printWriter.println("YOUR CHOICE");
            chosenDomino=yourChoice(clientFour, domToWrite, fourth);
            domPickedLastRound.add(chosenDomino);
            domToWrite.remove(chosenDomino);
            domToWriteInt.remove(Integer.valueOf(Integer.parseInt(chosenDomino)));
            clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
            clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
            clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
            printWriter.println("PLAYER CHOICE " + fourth + " " + chosenDomino);


            ArrayList<String> order=new ArrayList<>();


            String[][] board1=new String[201][201];
            String[][] board2=new String[201][201];
            String[][] board3=new String[201][201];
            String[][] board4=new String[201][201];

            board1=fillArrayWithZeos(board1);
            board2=fillArrayWithZeos(board2);
            board3=fillArrayWithZeos(board3);
            board4=fillArrayWithZeos(board4);

            board1[100][100]="X";
            board2[100][100]="X";
            board3[100][100]="X";
            board4[100][100]="X";

            ArrayList<String[][]> boards=new ArrayList();
            boards.add(board1);
            boards.add(board2);
            boards.add(board3);
            boards.add(board4);


//#####################################################################################################################################################################################################

            for (int p=0; p < 12; p++) {


                if (p != 11) {
                    domToWrite=pickDominos(dominos, random);
                    for (int j=0; j < domToWrite.size(); j++) {
                        domToWriteInt.add(Integer.valueOf(domToWrite.get(j)));
                    }
                    Collections.sort(domToWriteInt);

                    dominos=removePickedDominos(dominos, domToWrite);
                    System.out.println(domPickedLastRound);

                }


                order.add(first);
                order.add(second);
                order.add(third);
                order.add(fourth);


                for (int i=0; i < 4; i++) {
                    domPickedLastRoundInt.add(Integer.parseInt(domPickedLastRound.get(i)));
                }
                for (int i=0; i < 4; i++) {
                    domPickedLastRound.remove(0);
                }
                if (p != 11) {
                    out1.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    out2.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    out3.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    out4.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    printWriter.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    printWriter.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    printWriter.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                    printWriter.println("ROUND " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
                }


                System.out.println("PRZED: ");
                System.out.println(domPickedLastRoundInt);
                ArrayList<Integer> bricksPickedLastRound=new ArrayList<>(domPickedLastRoundInt);
                firstInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(firstInt - 1, 50);
                System.out.println(domPickedLastRoundInt);
                System.out.println("bricksPickedLastRound");
                System.out.println(bricksPickedLastRound);


                secondInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(secondInt - 1, 50);
                System.out.println(domPickedLastRoundInt);


                thirdInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(thirdInt - 1, 50);
                System.out.println(domPickedLastRoundInt);


                fourthInt=domPickedLastRoundInt.indexOf(Collections.min(domPickedLastRoundInt)) + 1;
                domPickedLastRoundInt.remove(Collections.min(domPickedLastRoundInt));
                domPickedLastRoundInt.add(fourthInt - 1, 50);
                System.out.println(domPickedLastRoundInt);


                clientsChange.add(clientOne);
                clientsChange.add(clientTwo);
                clientsChange.add(clientThree);
                clientsChange.add(clientFour);


                clientOne=clientsChange.get(firstInt - 1);
                clientTwo=clientsChange.get(secondInt - 1);
                clientThree=clientsChange.get(thirdInt - 1);
                clientFour=clientsChange.get(fourthInt - 1);

                first=order.get(firstInt - 1);
                second=order.get(secondInt - 1);
                third=order.get(thirdInt - 1);
                fourth=order.get(fourthInt - 1);


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

                if (p == 11) {
                    out1.println("ROUND");
                    out2.println("ROUND");
                    out3.println("ROUND");
                    out4.println("ROUND");
                    printWriter.println("ROUND");
                    printWriter.println("ROUND");
                    printWriter.println("ROUND");
                    printWriter.println("ROUND");
                }

                clientOne.out.println("YOUR MOVE");
                printWriter.println("YOUR MOVE");
                move=yourMove(clientOne, clientTwo, clientThree, clientFour, boards.get(Integer.parseInt(first) - 1), first, bricks, bricksPickedLastRound.get(firstInt - 1), first);

                if (p != 11) {
                    clientOne.out.println("YOUR CHOICE");
                    printWriter.println("YOUR CHOICE");
                    chosenDomino=yourChoice(clientOne, domToWrite, first);
                    domPickedLastRound.add(chosenDomino);
                    domToWrite.remove(chosenDomino);
                    clientTwo.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                    clientThree.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                    clientFour.out.println("PLAYER CHOICE " + first + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + first + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + first + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + first + " " + chosenDomino);
                }


                clientTwo.out.println("YOUR MOVE");
                printWriter.println("YOUR MOVE");
                move=yourMove(clientTwo, clientOne, clientThree, clientFour, boards.get(Integer.parseInt(second) - 1), third, bricks, bricksPickedLastRound.get(secondInt - 1), second);


                if (p != 11) {
                    clientTwo.out.println("YOUR CHOICE");
                    printWriter.println("YOUR CHOICE");
                    chosenDomino=yourChoice(clientTwo, domToWrite, second);
                    domPickedLastRound.add(chosenDomino);
                    domToWrite.remove(chosenDomino);
                    clientOne.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                    clientThree.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                    clientFour.out.println("PLAYER CHOICE " + second + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + second + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + second + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + second + " " + chosenDomino);
                }

                clientThree.out.println("YOUR MOVE");
                printWriter.println("YOUR MOVE");
                move=yourMove(clientThree, clientOne, clientTwo, clientFour, boards.get(Integer.parseInt(third) - 1), third, bricks, bricksPickedLastRound.get(thirdInt - 1), third);


                if (p != 11) {
                    clientThree.out.println("YOUR CHOICE");
                    printWriter.println("YOUR CHOICE");
                    chosenDomino=yourChoice(clientThree, domToWrite, third);
                    domPickedLastRound.add(chosenDomino);
                    domToWrite.remove(chosenDomino);
                    clientOne.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                    clientTwo.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                    clientFour.out.println("PLAYER CHOICE " + third + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + third + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + third + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + third + " " + chosenDomino);

                }


                clientFour.out.println("YOUR MOVE");
                printWriter.println("YOUR MOVE");
                move=yourMove(clientFour, clientOne, clientTwo, clientThree, boards.get(Integer.parseInt(fourth) - 1), fourth, bricks, bricksPickedLastRound.get(fourthInt - 1), fourth);


                if (p != 11) {
                    clientFour.out.println("YOUR CHOICE");
                    printWriter.println("YOUR CHOICE");
                    chosenDomino=yourChoice(clientFour, domToWrite, fourth);
                    domPickedLastRound.add(chosenDomino);
                    domToWrite.remove(chosenDomino);
                    clientOne.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                    clientThree.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                    clientTwo.out.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + fourth + " " + chosenDomino);
                    printWriter.println("PLAYER CHOICE " + fourth + " " + chosenDomino);


                }


                if (p != 11) {
                    for (int i=0; i < 4; i++) {
                        domPickedLastRoundInt.remove(0);
                        clientsChange.remove(0);
                        order.remove(0);
                    }
                    for (int i=0; i < 4; i++) {
                        domToWriteInt.remove(0);
                    }
                }

                if (p == 11) {

                    int board1points=countPoints(board1);
                    int board2points=countPoints(board2);
                    int board3points=countPoints(board3);
                    int board4points=countPoints(board4);

                    out1.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    out2.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    out3.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    out4.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    printWriter.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    printWriter.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    printWriter.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                    printWriter.println("GAME OVER RESULTS " + login1 + " " + board1points + " " + login2 + " " + board2points + " " + login3 + " " + board3points + " " + login4 + " " + board4points);
                }


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out1.close();
            out2.close();
            out3.close();
            out4.close();
            printWriter.close();

        }


        try {
            in1.close();
            in2.close();
            in3.close();
            in4.close();
            printWriter.close();


        } catch (IOException e) {
            printWriter.close();
            e.printStackTrace();
        }
    }


    private int countPoints(String[][] board1) {

        int pointsG=countPointsForType("g", board1);
        int pointsF=countPointsForType("f", board1);
        int pointsS=countPointsForType("s", board1);
        int pointsB=countPointsForType("b", board1);
        int pointsW=countPointsForType("w", board1);
        int pointsM=countPointsForType("m", board1);

        return (pointsG + pointsF + pointsS + pointsB + pointsW + pointsM);
    }

    private static int countPointsForType(String fieldType, String[][] board1) {
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        String[][] board1Copy=new String[201][201];
        for (int i=0; i < board1.length; i++)
            for (int j=0; j < board1[i].length; j++)
                board1Copy[i][j]=board1[i][j];


        result=setLabels(fieldType, board1Copy);


        Integer m;


        if (!(result.get(0).size() == (0))) {
            m=result.get(0).get(0);
        } else m=1;
        ArrayList<Integer> numOfLabels=result.get(0);
        ArrayList<Integer> valueOfLabels=result.get(0);
        int points=5;

        for (int j=1; j <= m; j++) {
            if (!(numOfLabels.size() == 0)) {
                points=1;
                points+=numOfLabels.get(0) * valueOfLabels.get(0);
            }
        }


        return points;

    }

    private static ArrayList<ArrayList<Integer>> setLabels(String fieldType, String[][] board1Copy) {
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        ArrayList<Integer> mValue=new ArrayList<>();
        int m=1;
        for (int y=0; y < 201; y++)
            for (int x=0; x < 201; x++) {
                if (board1Copy[x][y].matches(fieldType)) {
                    board1Copy=compLabel(x, y, m++, board1Copy, fieldType);
                }
            }
        for (int y=0; y < 201; y++)
            for (int x=0; x < 201; x++) {
                if (board1Copy[x][y].matches(fieldType + "*")) {
                    result=compLabelExtra(x, y, board1Copy, m);
                }
            }
        mValue.add(m);
        result.add(mValue);


        return result;
    }

    private static ArrayList<ArrayList<Integer>> compLabelExtra(int x, int y, String[][] board1Copy, int m) {
        ArrayList<Integer> values=new ArrayList<>();
        ArrayList<Integer> multiplyBy=new ArrayList<>();
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();


        int numberOfMs=0;
        for (int i=1; i <= m; i++) {
            for (int j=0; j < 201; j++)
                for (int k=0; k < 201; k++) {
                    if (board1Copy[j][k].equals(String.valueOf(m))) {
                        values.add(m - 1, numberOfMs++);
                    }
                }
            if (board1Copy[x - 1][y].matches(String.valueOf(m))) {
                multiplyBy.add(m - 1, board1Copy[x - 1][y].length());
            }
            if (board1Copy[x + 1][y].matches(String.valueOf(m))) {
                multiplyBy.add(m - 1, board1Copy[x + 1][y].length());

            }
            if (board1Copy[x][y - 1].matches(String.valueOf(m))) {
                multiplyBy.add(m - 1, board1Copy[x][y - 1].length());

            }

            if (board1Copy[x][y + 1].matches(String.valueOf(m))) {
                multiplyBy.add(m - 1, board1Copy[x][y + 1].length());

            }

        }
        result.add(values);
        result.add(multiplyBy);
        return result;


    }


    private static String[][] compLabel(int i, int j, int m, String[][] board1Copy, String fieldType) {

        if (board1Copy[i][j].matches(fieldType)) {
            board1Copy=setPixel(board1Copy, i, j, m);
            compLabel(i - 1, j, m, board1Copy, fieldType);
            compLabel(i + 1, j, m, board1Copy, fieldType);
            compLabel(i, j - 1, m, board1Copy, fieldType);
            compLabel(i, j + 1, m, board1Copy, fieldType);
        }
        return board1Copy;
    }

    private static String[][] setPixel(String[][] board1Copy, int i, int j, int m) {
        board1Copy[i][j]=String.valueOf(m);
        return board1Copy;
    }

    private String[][] fillArrayWithZeos(String[][] arr) {
        Arrays.stream(arr).forEach(a -> Arrays.fill(a, "0"));
        return arr;
    }


    private int startPrint(String first, String second, String third, String fourth, ArrayList<Integer> domToWriteInt, int index, PrintWriter out1, PrintWriter out2) {
        out1.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
        printWriter.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
        index++;
        out2.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
        printWriter.println("START " + index + " " + first + " " + second + " " + third + " " + fourth + " " + domToWriteInt.get(0) + " " + domToWriteInt.get(1) + " " + domToWriteInt.get(2) + " " + domToWriteInt.get(3));
        return index;
    }


    private String yourChoice(ClientHandler clientOne, ArrayList<String> domToWrite, String clientNumber) {

        String secSentence=null;
        String[] splittedChoice=new String[0];
        try {
            secSentence=clientOne.in.readLine();
            printWriter.println(secSentence);
            splittedChoice=secSentence.split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (splittedChoice[0].matches("CHOOSE")) {
            if (domToWrite.contains(splittedChoice[1])) {
                clientOne.out.println("OK");
                printWriter.println("OK");
                String chosenDomino=secSentence.split(" ")[1];
                return chosenDomino;
            }
        } else {
            clientOne.out.println("ERROR");
            printWriter.println("ERROR");
            errorCounter[Integer.parseInt(clientNumber) - 1]+=1;

            return yourChoice(clientOne, domToWrite, clientNumber);
        }
        clientOne.out.println("ERROR");
        printWriter.println("ERROR");
        errorCounter[Integer.parseInt(clientNumber) - 1]+=1;
        return yourChoice(clientOne, domToWrite, clientNumber);
    }

    private ArrayList<String> yourMove(ClientHandler clientOne, ClientHandler clientTwo, ClientHandler clientThree, ClientHandler clientFour, String[][] board, String clientNumber, String[][] bricks, Integer chosenDomino, String first) {
        ArrayList<String> result=new ArrayList<>();

        String secSentence=null;
        try {
            secSentence=clientOne.in.readLine();
            printWriter.println(secSentence);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(secSentence + " sec sentence");


        String x_coor=secSentence.split(" ")[1];
        String y_coor=secSentence.split(" ")[2];
        String orientation=secSentence.split(" ")[3];

        int x_coorInt=Integer.parseInt(x_coor);
        int y_coorInt=Integer.parseInt(y_coor);
        int orientationInt=Integer.parseInt(orientation);

        if (x_coorInt >= -100 && x_coorInt <= 100 && y_coorInt >= -100 && y_coorInt <= 100 && (orientationInt == 0 || orientationInt == 90 || orientationInt == 180 || orientationInt == 270)) {
            if (catchMoveMistake(x_coorInt, y_coorInt, orientationInt, board)) {
                errorCounter[Integer.parseInt(clientNumber) - 1]+=1;
                return yourMove(clientOne, clientTwo, clientThree, clientFour, board, clientNumber, bricks, chosenDomino, first);
            } else if (secSentence.equals("MOVE " + x_coor + " " + y_coor + " " + orientation)) {

                clientOne.out.println("OK");
                printWriter.println("OK");
                clientTwo.out.println("PLAYER MOVE " + first + ' ' + x_coor + " " + y_coor + " " + orientation);
                clientThree.out.println("PLAYER MOVE " + first + " " + x_coor + " " + y_coor + " " + orientation);
                clientFour.out.println("PLAYER MOVE " + first + " " + x_coor + " " + y_coor + " " + orientation);
                printWriter.println("PLAYER MOVE " + first + ' ' + x_coor + " " + y_coor + " " + orientation);
                printWriter.println("PLAYER MOVE " + first + ' ' + x_coor + " " + y_coor + " " + orientation);
                printWriter.println("PLAYER MOVE " + first + ' ' + x_coor + " " + y_coor + " " + orientation);
                result.add(x_coor);
                result.add(y_coor);
                result.add(orientation);


                x_coorInt+=100;
                y_coorInt+=100;
                System.out.println(x_coorInt);
                System.out.println(y_coorInt);
                System.out.println(chosenDomino);

                board[x_coorInt][y_coorInt]=bricks[chosenDomino - 1][0];
                if (orientation.equals("0")) {
                    board[x_coorInt + 1][y_coorInt]=bricks[chosenDomino - 1][1];
                }
                if (orientation.equals("90")) {
                    board[x_coorInt][y_coorInt + 1]=bricks[chosenDomino - 1][1];
                }
                if (orientation.equals("180")) {
                    board[x_coorInt - 1][y_coorInt]=bricks[chosenDomino - 1][1];
                }
                if (orientation.equals("270")) {
                    board[x_coorInt][y_coorInt - 1]=bricks[chosenDomino - 1][1];
                }

                return result;


            } else {
                clientOne.out.println("ERROR");
                printWriter.println("ERROR");
                errorCounter[Integer.parseInt(clientNumber) - 1]+=1;
                return yourMove(clientOne, clientTwo, clientThree, clientFour, board, clientNumber, bricks, chosenDomino, first);
            }

        } else {
            clientOne.out.println("ERROR");
            printWriter.println("ERROR");
            errorCounter[Integer.parseInt(clientNumber) - 1]+=1;
            return yourMove(clientOne, clientTwo, clientThree, clientFour, board, clientNumber, bricks, chosenDomino, first);
        }


    }

    private boolean catchMoveMistake(int x, int y, int orientation, String[][] board) {
        x+=100;
        y+=100;


        if (!board[x][y].equals("0")) {
            return true;
        } else if (orientation == 90) {
            if (!board[x][y + 1].equals("0")) {
                return true;
            } else {
                return (board[x][y-1].equals("0") && board[x + 1][y].equals("0") && board[x-1][y].equals("0") && board[x-1][y +1].equals("0") && board[x + 1][y + 1].equals("0") && board[x][y +2].equals("0"));
            }
        } else if (orientation == 0) {
            if (!board[x + 1][y].equals("0")) {
                return true;
            } else {
                return (board[x - 1][y].equals("0") && board[x][y-1].equals("0") && board[x][y + 1].equals("0") && board[x + 1][y - 1].equals("0") && board[x + 1][y + 1].equals("0") && board[x + 2][y].equals("0"));
            }
        } else if (orientation == 270) {
            if (!board[x][y - 1].equals("0")) {
                return true;
            } else {
                return (board[x][y + 1].equals("0") && board[x - 1][y].equals("0") && board[x + 1][y].equals("0") && board[x -1][y - 1].equals("0") && board[x + 1][y - 1].equals("0") && board[x][y - 2].equals("0"));
            }
        } else if (orientation == 180) {
            if (!board[x - 1][y].equals("0")) {
                return true;
            } else {
                return (board[x + 1][y].equals("0") && board[x-2][y].equals("0") && board[x][y + 1].equals("0") && board[x][y - 1].equals("0") && board[x - 1][y - 1].equals("0") && board[x -1][y+1].equals("0"));
            }
        } else
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