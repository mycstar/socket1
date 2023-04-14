package se211;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String argv[]) throws Exception {

        String clientData;
        String capitalizedData;

        ServerSocket serverS = new ServerSocket(6789);
        while (true) {

            Socket connSocket = serverS.accept();

            BufferedReader inClient = new BufferedReader(new
                    InputStreamReader(connSocket.getInputStream()));

            DataOutputStream outClient = new DataOutputStream(connSocket.getOutputStream());

            clientData = inClient.readLine();

            capitalizedData = clientData.toUpperCase() + '\n';

            outClient.writeBytes(capitalizedData);
        }
    }
}
