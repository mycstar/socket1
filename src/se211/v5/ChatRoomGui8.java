package se211.v5;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

public class ChatRoomGui8 extends JFrame implements ActionListener {

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JList<String> clientsList;
    private DefaultListModel<String> clientsModel;

    public ChatRoomGui8() {
        setTitle("Chat Room");
        setSize(600, 500);
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

        clientsModel = new DefaultListModel<>();
        clientsList = new JList<>(clientsModel);
        JScrollPane clientsScrollPane = new JScrollPane(clientsList);

        clientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String recipient = clientsList.getSelectedValue();
                if (recipient != null) {
                    JOptionPane.showMessageDialog(this, "Printing complete");
                    // new PrivateChat("You", recipient, chatArea);
                }
            }
        });

        JPanel clientsPanel = new JPanel(new BorderLayout());
        clientsPanel.setBorder(BorderFactory.createTitledBorder("Connected Clients"));
        clientsPanel.setPreferredSize(new Dimension(150, clientsPanel.getHeight()));
        clientsPanel.add(clientsScrollPane, BorderLayout.CENTER);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(chatScrollPane, BorderLayout.CENTER);
        container.add(messagePanel, BorderLayout.SOUTH);
        container.add(clientsPanel, BorderLayout.EAST);
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
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        String clientName = "client" + randomNum;

        ChatRoomGui6 chatRoom = new ChatRoomGui6();
        chatRoom.setVisible(true);

        // Test adding/removing clients
        List<String> clients = new ArrayList<>();
        clients.add("client1");
        clients.add("client2");
        clients.add("client3");

        for (String client : clients) {
            chatRoom.addClient(client);
        }

        chatRoom.removeClient("client2");
    }
}
