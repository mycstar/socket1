package se211.v5;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SThread5 extends Thread {
    Socket connSocket;
    String clientData;
    String capitalizedData;
    ConcurrentHashMap<String, Socket> clientList;
    String nickName;

    ConcurrentHashMap<String, ObjectOutputStream> clientOutputList;
    ObjectInputStream inputStream;

    public SThread5(String nickName1, Socket clientS, ConcurrentHashMap<String, Socket> clientListI, ObjectInputStream inputStream1, ConcurrentHashMap<String, ObjectOutputStream> clientOutputList1) {
        nickName = nickName1;
        connSocket = clientS;
        clientList = clientListI;
        inputStream = inputStream1;
        clientOutputList = clientOutputList1;
    }

    @Override
    public void run() {
        System.out.println("connected from " + connSocket.getRemoteSocketAddress());

        try {

            boolean userQuit = true;

            while (userQuit) {
                ChatMessage inMeg = (ChatMessage) inputStream.readObject();
                clientData = inMeg.getMessage();

                //System.out.println("data length: " + clientData.length());
                System.out.println(nickName + ":" + clientData);
                capitalizedData = clientData.toUpperCase();

                String outMeg = nickName + ":" + clientData;
                //outClient.println(capitalizedData);

                ChatMessage meg = new ChatMessage(0, outMeg);
                sendToAll(clientOutputList, meg);
 /*              for (Map.Entry<String, Socket> entity : clientList.entrySet()) {

                    String clientName = entity.getKey();
                    PrintWriter individualClient = new PrintWriter(entity.getValue().getOutputStream(),true);
                    individualClient.println(clientName+":"+clientData);
                }*/

                if (clientData.equals("quit")) {
                    userQuit = false;
                }
            }
            //outClient.close();
            inputStream.close();
            connSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendToAll(ConcurrentHashMap<String, ObjectOutputStream> clientList, ChatMessage meg) throws IOException {
        for (Map.Entry<String, ObjectOutputStream> entity : clientList.entrySet()) {

            String clientName = entity.getKey();
            System.out.println("send to client: "+ meg.getMessage());

            ObjectOutputStream individualClient = entity.getValue();

            individualClient.writeObject(meg);
            //individualClient.flush();
        }
    }

/*    private static void sendToAll(ConcurrentHashMap<String, Socket> clientList, ChatMessage meg) throws IOException {
        for (Map.Entry<String, Socket> entity : clientList.entrySet()) {

            String clientName = entity.getKey();
            ObjectOutputStream individualClient = new ObjectOutputStream(entity.getValue().getOutputStream());
            individualClient.writeObject(meg);
        }
    }*/
}




