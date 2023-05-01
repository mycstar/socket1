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
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ChatMessage meg = new ChatMessage(ChatMessage.USERNAME,nickName);
        meg.setSender(nickName);

        outToServer.writeObject(meg);

        clientList = getClientsList(inFromServer);
        chatRoom.updateClients(clientList);

        boolean endChat = false;

        CThread5 cthread = new CThread5(clientSocket, chatRoom);
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

    private List<String> getClientsList(BufferedReader inFromServer) throws IOException {
        String clientStr = inFromServer.readLine();
        ArrayList<String> clientList = new ArrayList<>();
        String[] retArr = clientStr.split(",");
        for (String nickName : retArr) {
            clientList.add(nickName);
        }
        return clientList;
    }


}
