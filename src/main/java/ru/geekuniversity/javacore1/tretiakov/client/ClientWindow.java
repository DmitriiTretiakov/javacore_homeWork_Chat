package ru.geekuniversity.javacore1.tretiakov.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientWindow extends JFrame {
    private JTextField message;
    private JTextArea chatHistory;
    private JTextField login;
    private JPasswordField password;
    private JPanel top;
    private JPanel bottom;
    private JButton send;

    private ClientConnection clientConnection;

    public ClientWindow(){
        clientConnection = new ClientConnection();
        clientConnection.init(this);

        setTitle("Chat");
        setSize(500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        chatHistory.setLineWrap(true);

        JScrollPane jScrollPane = new JScrollPane(chatHistory);

        bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.setPreferredSize(new Dimension(100, 50));

        send = new JButton("Send");
        message = new JTextField();
        message.setPreferredSize(new Dimension(100, 50));

        login = new JTextField();
        password = new JPasswordField();
        JButton auth = new JButton("Login");
        top = new JPanel(new GridLayout(1,3));
        top.add(login);
        top.add(password);
        top.add(auth);

        bottom.add(send, BorderLayout.EAST);
        bottom.add(message, BorderLayout.CENTER);

        add(jScrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        chatHistory.setBackground(Color.DARK_GRAY);
        chatHistory.setForeground(Color.LIGHT_GRAY);
        message.setBackground(Color.DARK_GRAY);
        message.setForeground(Color.LIGHT_GRAY);
        send.setBackground(Color.GRAY);
        send.setForeground(Color.LIGHT_GRAY);
        login.setBackground(Color.DARK_GRAY);
        login.setForeground(Color.LIGHT_GRAY);
        password.setBackground(Color.DARK_GRAY);
        password.setForeground(Color.LIGHT_GRAY);
        auth.setBackground(Color.GRAY);
        auth.setForeground(Color.LIGHT_GRAY);

        bottom.grabFocus();

        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!message.getText().trim().isEmpty()) {
                    System.out.println("Your message: " + message.getText());
                    sendMessage();
                    message.setText("");
                }
            }
        });

        message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!message.getText().trim().isEmpty()) {
                    System.out.println("Your message: " + message.getText());
                    sendMessage();
                    message.setText("");
                }
            }
        });

        auth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                auth();
            }
        });

        switchWindows();
        setVisible(true);
    }

    public void sendMessage(){
        String message = this.message.getText();
        this.message.setText("");
        clientConnection.sendMessage(message);
    }

    public void auth(){
        clientConnection.auth(login.getText(), new String(password.getPassword()));
        login.setText("");
        password.setText("");
    }

    public void showMessage(String message){
        chatHistory.append(message + "\n");
        chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
    }

    public void switchWindows(){
        top.setVisible(!clientConnection.isAuthrozied());
        bottom.setVisible(clientConnection.isAuthrozied());
    }
}

