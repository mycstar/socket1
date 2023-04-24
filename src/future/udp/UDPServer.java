package future.udp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String args[]) throws Exception {

        byte[] receiveData = new byte[2048];
        byte[] sendData = new byte[1024];

        DatagramSocket serverSocket = new DatagramSocket(9876);

        while (true) {

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);
            String receivedPacket = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            System.out.println("Receive " + IPAddress.getHostAddress() + " Data: " + receivedPacket);

            String capitalizedStr = receivedPacket.toUpperCase();
            sendData = capitalizedStr.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, capitalizedStr.length(), IPAddress, port);

            System.out.println("Send: " + sendData);
            serverSocket.send(sendPacket);
        }
    }


    public void receiveAndSend() throws IOException {

        DatagramSocket serverSocket = new DatagramSocket(9876);

        byte[] receiveData = new byte[2048];
        byte[] sendData = new byte[1024];

        while (true) {

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println("Receive " + IPAddress.getHostAddress() + " Data: " + sentence);

//            InputStreamReader sr = new InputStreamReader(new ByteArrayInputStream(receivePacket.getData()));
//            BufferedReader bf = new BufferedReader(sr);
            //String lineStr = bf.readLine();
//            System.out.println("Receive packet length: "+ receivePacket.getLength());
//            System.out.println("Receive length: "+ sentence.length());

            int port = receivePacket.getPort();

            String capitalizedSentence = sentence.toUpperCase();

            sendData = capitalizedSentence.getBytes();

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, capitalizedSentence.length(), IPAddress,
                            port);

            System.out.println("Send: " + sendData);

            serverSocket.send(sendPacket);

        }

    }
}
