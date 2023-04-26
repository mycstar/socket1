package se211.v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public static void main(String argv[]) throws Exception {

        String data;
        String editedData;
        Scanner scn = new Scanner(System.in);
        Socket clientSocket = new Socket("localhost", 6789);

        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        boolean endChat = false;

        CThread cthread  = new CThread(clientSocket);
        cthread.start();

        while(!endChat) {
            data = scn.nextLine();
            //System.out.println("the length:" + data.length() + " " + data);

            outToServer.println(data);

//            editedData = inFromServer.readLine();
//            System.out.println("Server reply: " + editedData);
//
            if(data.equals("quit")){
                endChat = true;
            }
        }
        clientSocket.close();
        inFromServer.close();
        outToServer.close();
        cthread.stop();

    }

}
