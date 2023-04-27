package se211.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class CThread extends Thread {
    Socket connSocket;
    boolean socketClose = false;

    public CThread(Socket clientS) {
        connSocket = clientS;
    }

    public void run() {
        try {

            while(!socketClose) {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                String editedData = null;

                editedData = inFromServer.readLine();
                System.out.println("Server reply: " + editedData);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit(){
        socketClose = true;
    }
}
