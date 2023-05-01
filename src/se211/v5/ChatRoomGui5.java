package se211.v5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ChatRoomGui5 extends JFrame implements ActionListener {

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatRoomGui5() {
        setTitle("Chat Room");
        setSize(400, 500);
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

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(chatScrollPane, BorderLayout.CENTER);
        container.add(messagePanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            String message = messageField.getText();
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        String clientName = "client"+ randomNum;

        ChatRoomGui5 chatRoom = new ChatRoomGui5();
        chatRoom.setVisible(true);
    }




}

