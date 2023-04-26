package se211.v3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.concurrent.ConcurrentHashMap;

public class BServer {

    static ConcurrentHashMap<String, User> userList = new ConcurrentHashMap<String,User>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        int connNum = 0;

        while (true) {
            Socket connSocket = serverS.accept();

            User user = new User();
            user.setInetadd(connSocket.getInetAddress());

            System.out.println("Connection number:"+ connNum);
            Athread a = new Athread(connSocket, user, userList);
            a.start();
        }
    }
}
