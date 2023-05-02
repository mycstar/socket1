package se211.v5;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomGuiWithPrivate extends JFrame implements ActionListener {

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JList<String> clientList;
    private DefaultListModel<String> clientsModel;

    public ChatRoomGuiWithPrivate() {
        setTitle("Chat Room");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(Color.lightGray);
        chatArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        messageField = new JTextField(30);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        JPanel clientPanel = new JPanel(new BorderLayout());
        JLabel clientLabel = new JLabel("Connected User");
        clientPanel.add(clientLabel, BorderLayout.NORTH);

        //String[] clientNames = {"client1", "client2", "client3", "client4", "client5"};
        clientsModel = new DefaultListModel<>();
        clientList = new JList<>(clientsModel);
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String recipient = clientList.getSelectedValue();
                if (recipient != null) {
                    //JOptionPane.showMessageDialog(this, "Printing complete");
                    try {
                        File file = new File("abc.txt");
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                        PrivateChat privateW = new PrivateChat("You", recipient, out);
                        privateW.setVisible(true);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        JScrollPane clientScrollPane = new JScrollPane(clientList);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(chatScrollPane, BorderLayout.CENTER);
        container.add(messagePanel, BorderLayout.SOUTH);
        container.add(clientPanel, BorderLayout.EAST);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            String message = messageField.getText();
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
        }
    }

    public void addClient(String clientName) {
        clientsModel.addElement(clientName);
    }

    public void removeClient(String clientName) {
        clientsModel.removeElement(clientName);
    }

    public static void main(String[] args) {
        ChatRoomGuiWithPrivate chatRoom = new ChatRoomGuiWithPrivate();
        chatRoom.setVisible(true);

        // Test adding/removing clients
        List<String> clients = new ArrayList<>();
        clients.add("client1");
        clients.add("client2");
        clients.add("client3");

        for (String client : clients) {
            chatRoom.addClient(client);
        }
    }

}
