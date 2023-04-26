package se211.v1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TCPServer {
    public static void main(String argv[]) throws Exception {

        String clientData;
        String capitalizedData;

        //Monitor a specify port
        ServerSocket serverS = new ServerSocket(6789);



        System.out.println("server is running...");
        while (true) {

            Socket connSocket = serverS.accept();
            System.out.println("connected from " + connSocket.getRemoteSocketAddress());
            BufferedReader inClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

            DataOutputStream outClient = new DataOutputStream(connSocket.getOutputStream());

            clientData = inClient.readLine();
            int inCh;
            char[] inChAr = new char[64];
            while((inCh = inClient.read(inChAr))!=-1){

                String inStr = inChAr.toString();
                System.out.println("data length: " + inChAr.length);
                System.out.println(clientData);

                capitalizedData = clientData.toUpperCase() + System.lineSeparator();

                outClient.writeBytes(capitalizedData);

            }


        }
    }
}
