package se211.v2;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AThread extends Thread {
    Socket connSocket;
    String clientData;
    String capitalizedData;
    int clientNum;
    ConcurrentHashMap<Integer, Socket> clientList;

    public AThread(int clientnumi, Socket clientS, ConcurrentHashMap<Integer, Socket> clientListI) {
        connSocket = clientS;
        clientNum = clientnumi;
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

                System.out.println("data length: " + clientData.length());
                System.out.println(clientData);
                //capitalizedData = clientData.toUpperCase();
                capitalizedData = "Client " + clientNum +" say: "+ clientData;
                //outClient.println(capitalizedData);

                if (clientData.equals("quit")) {
                    userQuit = false;
                } else {
                    System.out.println("total client  : " + clientList.values().size());
                    for (Map.Entry<Integer, Socket> entity : clientList.entrySet()) {

                        int clientNumL = entity.getKey();
                        System.out.println("try to response to : " + clientNumL);
                        Socket clientSocket = entity.getValue();
                        if(clientSocket.isClosed()){
                            System.out.println(clientNumL + " closed, move to next one" );
                        }else {
                            PrintWriter individualClient = new PrintWriter(entity.getValue().getOutputStream(), true);
                            individualClient.println(capitalizedData);
                        }
                    }
                }
            }
            //outClient.close();
            System.out.println("client " + clientNum + " exit!");
            clientList.remove(clientNum);
            System.out.println("total client after clean : " + clientList.values().size());
            inClient.close();
            connSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




