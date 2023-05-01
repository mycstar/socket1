package se211.v5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class CThread5 extends Thread {
    Socket connSocket;
    ChatRoomGui6 chatRoom;

    public CThread5(Socket clientS, ChatRoomGui6 chatRoom1) {
        connSocket = clientS;
        chatRoom = chatRoom1;

    }

    public void run() {
        try {
            while(!connSocket.isClosed()) {
                ObjectInputStream inFromServer = new ObjectInputStream(connSocket.getInputStream());
                String editedData = null;

                ChatMessage reObj = (ChatMessage)inFromServer.readObject();
                editedData = reObj.getMessage();

                if(reObj.getType()==ChatMessage.MESSAGE) {
                    System.out.println(editedData);
                    chatRoom.updateChat(editedData);
                }else if(reObj.getType()==ChatMessage.USERNAME){

                    List<String>   clientList = getClientsList(reObj);

                    ChatRoomGui6.updateClients(clientList);
                }
            }

        } catch (SocketException e) {
            System.out.println("client quit");
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception);
        }
    }



    private List<String> getClientsList(ChatMessage meg) throws IOException {
        String clientStr = meg.getMessage();
        ArrayList<String> clientList = new ArrayList<>();
        String[] retArr = clientStr.split(",");
        for (String nickName : retArr) {
            clientList.add(nickName);
        }
        return clientList;
    }

}
