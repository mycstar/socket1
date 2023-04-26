package se211.v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class TCPClient {

    public static void main(String argv[]) throws Exception {

        String data;
        String editedData;

        Random random = new Random();
        String nickName = "Tom" + random.nextInt();

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        Scanner scn = new Scanner(System.in);

        Socket clientSocket = new Socket("localhost", 6789);

        PrintStream outToServer = new PrintStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        nameClaim(outToServer,inFromServer,nickName);

        boolean endChat = false;

        while(!endChat) {
            //data = inFromUser.readLine();
            data = scn.nextLine();
            System.out.println("the length:" + data.length() + " " + data);

            // outToServer.writeBytes(data + '\n');
            outToServer.println(data);

            editedData = inFromServer.readLine();

            System.out.println("Server reply: " + editedData);
            if(data.equals("quit")){
                endChat = true;
            }
        }
        clientSocket.close();
        inFromServer.close();
        outToServer.close();
    }

    public static void nameClaim(PrintStream outToServer,BufferedReader inFromServer, String nickName) throws IOException {

        outToServer.println(nickName);

        String editedData = inFromServer.readLine();

        System.out.println("Server reply: " + editedData);
    }



}
