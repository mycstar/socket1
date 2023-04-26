package se211.v3;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Athread extends Thread {
    Socket connSocket;
    String clientData;
    String capitalizedData;

    User user ;
    ConcurrentHashMap<String, User> userList;
    public Athread(Socket clientS, User userI, ConcurrentHashMap<String, User> userListI) {
        connSocket = clientS;
        user = userI;
        userList = userListI;

    }

    public void run() {

        System.out.println("connected from " + connSocket.getRemoteSocketAddress());
        boolean welcome = true;

        try {

            BufferedReader inClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

            PrintStream outClient = new PrintStream(connSocket.getOutputStream());

            if(welcome){

                nameClaim(outClient,inClient);
                welcome = false;

            }else {

                clientData = inClient.readLine();

                System.out.println("data length: " + clientData.length());
                System.out.println(clientData);

                capitalizedData = clientData.toUpperCase() ;

                outClient.println(capitalizedData);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  void nameClaim(PrintStream outToServer, BufferedReader inClient) throws IOException {

       String  nickname = inClient.readLine();

        System.out.println("data length: " + nickname.length());

        user.setNickName(nickname);
        userList.put(nickname,user);

        System.out.println(nickname+ " add to user List");

        String welcomeStr = "hi "+nickname;
        outToServer.println(welcomeStr);

        System.out.println("Server reply: " + welcomeStr);
    }
}




