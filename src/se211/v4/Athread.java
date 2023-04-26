package se211.v4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Athread extends Thread {
    Socket connSocket;
    //String clientData;
    String capitalizedData;

    public Athread(Socket clientS) {

        connSocket = clientS;
    }

    public void run() {
        System.out.println("connected from " + connSocket.getRemoteSocketAddress());

        try {

            BufferedReader inClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            PrintWriter outClient = new PrintWriter(connSocket.getOutputStream(),true);

            boolean clientQuit = false;
            while(!clientQuit) {
                String clientData = inClient.readLine();

                if(clientData.equals("quit")){
                    clientQuit = true;
                }else {
                    System.out.println("data length: " + clientData.length());
                    System.out.println(clientData);
                    capitalizedData = clientData.toUpperCase();

                    outClient.println(capitalizedData);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




