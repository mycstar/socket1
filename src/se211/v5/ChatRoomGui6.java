package se211.v5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatRoomGui6 extends JFrame implements ActionListener {

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private static JPanel clientsPanel;
    private static ArrayList<String> connectedClients;
    private static String clientName;

    public ChatRoomGui6() {
        setTitle("Chat Room");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        messageField = new JTextField(30);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));

        Dimension preferredSize = new Dimension(100, Integer.MAX_VALUE);
        clientsPanel.setPreferredSize(preferredSize);

        JScrollPane clientsScrollPane = new JScrollPane(clientsPanel);

        connectedClients = new ArrayList<String>();

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(chatScrollPane, BorderLayout.CENTER);
        container.add(messagePanel, BorderLayout.SOUTH);
        container.add(clientsPanel, BorderLayout.EAST);
    }

    public void updateChat( String message) {

        chatArea.append(message + "\n");
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            String message = messageField.getText();
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
        }
    }

    public void addClient(String clientName) {
        connectedClients.add(clientName);
        clientsPanel.add(new JLabel(clientName));
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    public static void updateClients(List<String> clientList){
        boolean isFirst = true;
        for (String nickname:connectedClients) {
            if(isFirst){

            }else {
                connectedClients.remove(nickname);
                Component[] components = clientsPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JLabel) {
                        if (((JLabel) component).getText().equals(nickname)) {
                            clientsPanel.remove(component);
                        }
                    }
                }
            }
        }

        for (String nickname:clientList) {
            if(!nickname.equals(clientName)) {
                connectedClients.add(nickname);
                clientsPanel.add(new JLabel(nickname));
            }
        }
        clientsPanel.revalidate();
        clientsPanel.repaint();

    }
    public void removeClient(String clientName) {
        connectedClients.remove(clientName);
        Component[] components = clientsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                if (((JLabel) component).getText().equals(clientName)) {
                    clientsPanel.remove(component);
                }
            }
        }
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        clientName = "client" + randomNum;

        try {

            ChatRoomGui6 chatRoom = new ChatRoomGui6();
            chatRoom.setVisible(true);
            chatRoom.addClient(clientName);

            TCPClient5 megClient = new TCPClient5();
            megClient.chat(clientName, chatRoom);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }



}

