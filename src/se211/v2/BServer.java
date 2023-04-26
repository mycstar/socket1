package se211.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        int connNum = 0;
        while (true) {
            Socket connSocket = serverS.accept();
            System.out.println("Connection number:"+ connNum);
            Athread a = new Athread(connSocket);
            a.start();
        }
    }
}
