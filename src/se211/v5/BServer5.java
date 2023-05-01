package se211.v5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BServer5 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        ConcurrentHashMap<String, Socket> clientList = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, ObjectOutputStream> clientOutputList = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, ObjectInputStream> clientInputList = new ConcurrentHashMap<>();


        int connNum = 0;
        while (true) {
            Socket connSocket = serverS.accept();
            System.out.println("Connection number:"+ connNum);
            //clientList.put(String.valueOf(connSocket.getPort()), connSocket);

            ObjectInputStream in = new ObjectInputStream(connSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(connSocket.getOutputStream());

            ChatMessage clientMeg = (ChatMessage) in.readObject();
            String clientName = clientMeg.getSender();
            System.out.println(clientMeg.getSender() +" connected");

            // add new client to HashMap
            clientList.put(clientName, connSocket);
            clientInputList.put(clientName, in);
            clientOutputList.put(clientName, out);

            // reminder all connected clients
            ChatMessage clientNameMeg = getClientNames(clientList);
            clientNameMeg.setSender("Server");
            sendToAll(clientOutputList,clientNameMeg);

            SThread5 a = new SThread5(clientName, connSocket, clientList, in, clientOutputList);
            a.start();
            connNum++;
            System.out.println("Total client number: " + connNum);
        }
    }

    private static void sendToAll(ConcurrentHashMap<String, ObjectOutputStream> clientList, ChatMessage meg) throws IOException {
        for (Map.Entry<String, ObjectOutputStream> entity : clientList.entrySet()) {

            String clientName = entity.getKey();
            System.out.println("send reminder of new client:"+ meg.getMessage());
            ObjectOutputStream individualClient = entity.getValue();

            individualClient.writeObject(meg);
            //individualClient.flush();
        }
    }
    private static ChatMessage getClientNames(ConcurrentHashMap<String, Socket> clientList) {
        StringBuffer clientsStr = new StringBuffer();
        for (Map.Entry<String, Socket> entity : clientList.entrySet()
             ) {
            clientsStr.append(entity.getKey());
            clientsStr.append(",");
        }
        if(clientsStr.substring(clientsStr.length()-1).equals(",")) {
            clientsStr.deleteCharAt(clientsStr.length()-1);
        }

        ChatMessage retMeg = new ChatMessage(1,clientsStr.toString());

       return retMeg;
    }
}
