package se211.v1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public static void main(String argv[]) throws Exception {

        String data;
        String editedData;

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        Scanner scn = new Scanner(System.in);

        Socket clientSocket = new Socket("localhost", 9876);

        PrintStream outToServer = new PrintStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        boolean endChat = false;
        while(!endChat) {
            //data = inFromUser.readLine();
            data = scn.nextLine();
            System.out.println("the length:" + data.length() + " " + data);

            // outToServer.writeBytes(data + '\n');
            outToServer.println(data);

            editedData = inFromServer.readLine();

            System.out.println("Server reply: " + editedData);


        }
        clientSocket.close();
    }

}
