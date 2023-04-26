package se211.V2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BServer {

    public static void main(String[] args) throws IOException {
/*
        Athread a = new Athread("Monday");
        a.start ();
        Athread b = new Athread("Tuesday");
        b.start ();
*/
        //Monitor a specify port
        ServerSocket serverS = new ServerSocket(6789);
        System.out.println("server is running...");
        while (true) {
            Socket connSocket = serverS.accept();
           Athread a = new Athread(connSocket);
           a.start();
        }
    }
}
