package se211.v2;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SThread extends Thread {
    Socket connSocket;
    String clientData;
    String capitalizedData;
    HashMap<String, Socket> clientList;

    public SThread(Socket clientS, HashMap<String, Socket> clientListI) {
        connSocket = clientS;
        clientList = clientListI;
    }

    public void run() {
        System.out.println("connected from " + connSocket.getRemoteSocketAddress());

        try {
            BufferedReader inClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            PrintWriter outClient = new PrintWriter(connSocket.getOutputStream());

            boolean useQuie = false;

            while(useQuie) {

                clientData = inClient.readLine();

                System.out.println("data length: " + clientData.length());
                System.out.println(clientData);
                capitalizedData = clientData.toUpperCase();

                outClient.println(capitalizedData);

/*                for (Map.Entry<String, Socket> entity : clientList.entrySet()) {

                    String clientSocket = entity.getKey();
                    PrintWriter individualClient = new PrintWriter(entity.getValue().getOutputStream());
                    individualClient.println(capitalizedData);
                }*/

                if(clientData.equals("quit")){
                    useQuie = true;
                }
            }
            outClient.flush();
            inClient.close();
            connSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




