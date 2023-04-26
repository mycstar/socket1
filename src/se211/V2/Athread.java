package se211.V2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class Athread extends Thread {
    Socket connSocket;
    String clientData;
    String capitalizedData;

    public Athread(Socket clientS) {
        connSocket = clientS;
    }

    public void run() {
        System.out.println("connected from " + connSocket.getRemoteSocketAddress());

        try {

            BufferedReader inClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

            DataOutputStream outClient = new DataOutputStream(connSocket.getOutputStream());

            clientData = inClient.readLine();

            System.out.println("data length: " + clientData.length());
            System.out.println(clientData);

            capitalizedData = clientData.toUpperCase() + System.lineSeparator();

            outClient.writeBytes(capitalizedData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




