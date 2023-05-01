package se211.v5;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TCPClient5 {

    public List<String> getClientList() {
        return clientList;
    }

    public void setClientList(List<String> clientList) {
        this.clientList = clientList;
    }

    List<String> clientList;

    public void chat(String nickName, ChatRoomGui6 chatRoom) throws Exception {

        String data;
        String editedData;
        Scanner scn = new Scanner(System.in);
        Socket clientSocket = new Socket("localhost", 6789);

        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());


        ChatMessage meg = new ChatMessage(1,nickName);
        meg.setSender(nickName);

        outToServer.writeObject(meg);

        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        clientList = getClientsList(inFromServer);
        chatRoom.updateClients(clientList);

        boolean endChat = false;

        CThread5 cthread = new CThread5(clientSocket, chatRoom, inFromServer, outToServer);
        cthread.start();

        while (!endChat) {
            data = scn.nextLine();
            outToServer.writeObject(data);

            if (data.equals("quit")) {
                endChat = true;
            }
        }
        clientSocket.close();
        inFromServer.close();
        outToServer.close();
    }

    private List<String> getClientsList(ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
        Object obj = inFromServer.readObject();
        ChatMessage meg = (ChatMessage)obj;
        String clientStr = meg.getMessage();
        ArrayList<String> clientList = new ArrayList<>();
        String[] retArr = clientStr.split(",");
        for (String nickName : retArr) {
            clientList.add(nickName);
        }
        return clientList;
    }


}
