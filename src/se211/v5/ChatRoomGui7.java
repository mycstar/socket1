package se211.v5;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.util.ArrayList;
        import java.util.Random;

public class ChatRoomGui7 extends JFrame implements ActionListener {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JPanel clientsPanel;
    private ArrayList<String> connectedClients;
    private static final int CLIENTS_PANEL_DEFAULT_WIDTH = 150;

    public ChatRoomGui7() {
        setTitle("Chat Room");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        messageField = new JTextField(30);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        JPanel messagePanel = new ClientPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        clientsPanel.setPreferredSize(new Dimension(CLIENTS_PANEL_DEFAULT_WIDTH, 0));

        JScrollPane clientsScrollPane = new JScrollPane(clientsPanel);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(chatScrollPane, BorderLayout.CENTER);
        container.add(messagePanel, BorderLayout.SOUTH);
        container.add(clientsScrollPane, BorderLayout.EAST);

        connectedClients = new ArrayList<>();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
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
        JLabel clientLabel = new JLabel(clientName);
        clientsPanel.add(clientLabel);
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    public void removeClient(String clientName) {
        connectedClients.remove(clientName);
        Component[] components = clientsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel && ((JLabel) component).getText().equals(clientName)) {
                clientsPanel.remove(component);
                break;
            }
        }
        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        String clientName = "client" + randomNum;

        ChatRoomGui6 chatRoom = new ChatRoomGui6();
        chatRoom.setVisible(true);
        chatRoom.addClient(clientName);
        chatRoom.addClient("haha");
        chatRoom.addClient("cadfa");
        chatRoom.addClient("wwsf");
    }
}
