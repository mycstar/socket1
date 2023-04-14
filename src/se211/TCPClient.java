package se211;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

    public static void main(String argv[]) throws Exception {

        String data;
        String editedData;

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //data = inFromUser.readLine();
        data = "Hello\u001a";

       // outToServer.writeBytes(data + '\n');
        outToServer.writeBytes(data );

        editedData = inFromServer.readLine();

        System.out.println("Server reply: " + editedData);

        clientSocket.close();
    }

}
