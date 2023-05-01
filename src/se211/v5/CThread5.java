package se211.v5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class CThread5 extends Thread {
    Socket connSocket;

    public CThread5(Socket clientS) {
        connSocket = clientS;
    }

    public void run() {
        try {
            while(!connSocket.isClosed()) {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                String editedData = null;

                editedData = inFromServer.readLine();
                System.out.println(editedData);
            }

        } catch (SocketException e) {
            System.out.println("client quit");
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

}
