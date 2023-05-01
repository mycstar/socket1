package se211.v5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BServer5 {

    public static void main(String[] args) throws IOException {

        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        ConcurrentHashMap<String, Socket> clientList = new ConcurrentHashMap<>();

        int connNum = 0;
        while (true) {
            Socket connSocket = serverS.accept();
            System.out.println("Connection number:"+ connNum);
            //clientList.put(String.valueOf(connSocket.getPort()), connSocket);

            BufferedReader in = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            PrintWriter out = new PrintWriter(connSocket.getOutputStream(),true);


            String clientName = in.readLine();
            System.out.println(clientName +" connected");


            // send list of connected clients to new client
            String clientNamesStr = getClientNames(clientList);
            out.println(clientNamesStr);

            // add new client to HashMap
            clientList.put(clientName, connSocket);

            SThread5 a = new SThread5(clientName, connSocket, clientList);
            a.start();
            connNum++;
            System.out.println("Total client number: " + connNum);
        }
    }

    private static String getClientNames(ConcurrentHashMap<String, Socket> clientList) {
        StringBuffer clientsStr = new StringBuffer();
        for (Map.Entry<String, Socket> entity : clientList.entrySet()
             ) {
            clientsStr.append(entity.getKey());
        }

       return clientsStr.toString();
    }
}
