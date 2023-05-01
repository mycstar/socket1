package se211.v5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BServer5 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        ConcurrentHashMap<String, Socket> clientList = new ConcurrentHashMap<>();

        int connNum = 0;
        while (true) {
            Socket connSocket = serverS.accept();
            System.out.println("Connection number:"+ connNum);
            //clientList.put(String.valueOf(connSocket.getPort()), connSocket);

            ObjectInputStream in = new ObjectInputStream(connSocket.getInputStream());
            //ObjectOutputStream out = new ObjectOutputStream(connSocket.getOutputStream());

            ChatMessage clientMeg = (ChatMessage) in.readObject();
            String clientName = clientMeg.getSender();
            System.out.println(clientMeg.getSender() +" connected");

            // add new client to HashMap
            clientList.put(clientName, connSocket);

            // reminder all connected clients
            ChatMessage clientNameMeg = getClientNames(clientList);
            clientNameMeg.setSender("Server");
            sendToAll(clientList,clientNameMeg);

            SThread5 a = new SThread5(clientName, connSocket, clientList);
            a.start();
            connNum++;
            System.out.println("Total client number: " + connNum);
        }
    }

    private static void sendToAll(ConcurrentHashMap<String, Socket> clientList, ChatMessage meg) throws IOException {
        for (Map.Entry<String, Socket> entity : clientList.entrySet()) {

            String clientName = entity.getKey();
            ObjectOutputStream individualClient = new ObjectOutputStream(entity.getValue().getOutputStream());
            individualClient.writeObject(meg);
            //individualClient.flush();
        }
    }
    private static ChatMessage getClientNames(ConcurrentHashMap<String, Socket> clientList) {
        StringBuffer clientsStr = new StringBuffer();
        for (Map.Entry<String, Socket> entity : clientList.entrySet()
             ) {
            clientsStr.append(entity.getKey());
        }

        ChatMessage retMeg = new ChatMessage(1,clientsStr.toString());
       return retMeg;
    }
}
