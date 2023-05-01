package se211.v5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SThread5 extends Thread {
    Socket connSocket;
    String clientData;
    String capitalizedData;
    ConcurrentHashMap<String, Socket> clientList;
    String nickName;

    public SThread5(String nickName1, Socket clientS, ConcurrentHashMap<String, Socket> clientListI) {
        nickName = nickName1;
        connSocket = clientS;
        clientList = clientListI;
    }

    @Override
    public void run() {
        System.out.println("connected from " + connSocket.getRemoteSocketAddress());

        try {
            BufferedReader inClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            //PrintWriter outClient = new PrintWriter(connSocket.getOutputStream(), true);

            boolean userQuit = true;

            while (userQuit) {
                clientData = inClient.readLine();

                //System.out.println("data length: " + clientData.length());
                System.out.println(nickName+":"+ clientData);
                capitalizedData = clientData.toUpperCase();

                //outClient.println(capitalizedData);

               for (Map.Entry<String, Socket> entity : clientList.entrySet()) {

                    String clientName = entity.getKey();
                    PrintWriter individualClient = new PrintWriter(entity.getValue().getOutputStream(),true);
                    individualClient.println(clientName+":"+clientData);
                }

                if (clientData.equals("quit")) {
                    userQuit = false;
                }
            }
            //outClient.close();
            inClient.close();
            connSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



