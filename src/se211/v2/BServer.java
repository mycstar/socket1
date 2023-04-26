package se211.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        HashMap<String, Socket> clientList = new HashMap<>();

        int connNum = 0;
        while (true) {
            Socket connSocket = serverS.accept();
            System.out.println("Connection number:"+ connNum);
            clientList.put(String.valueOf(connSocket.getPort()), connSocket);

            SThread a = new SThread(connSocket, clientList);
            a.start();
        }
    }
}
