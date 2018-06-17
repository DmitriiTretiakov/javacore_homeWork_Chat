package ru.geekuniversity.javacore1.tretiakov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ClientWindow extends JFrame {
    private final String SERVER_ADDR = "Localhost";
    private final int SERVER_PORT = 8189 ;
    private Socket sock ;
    private Scanner scanner;
    private PrintWriter ptw ;

    JTextArea jta;
    JTextField jtf;
    JButton jb;
    JPanel jp;
    JFrame frame;

    public  ClientWindow (){

        try {
            sock = new Socket ( SERVER_ADDR, SERVER_PORT );
            scanner = new Scanner ( sock.getInputStream ());
            ptw = new PrintWriter ( sock.getOutputStream ());
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame = new JFrame();
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0,0,600,600);

        jta = new JTextArea();

        jta.setEditable(false);
        jta.setLineWrap(true);
        jp = new JPanel(new BorderLayout());
        add(jp, BorderLayout.SOUTH);
        jp.add(jta, BorderLayout.NORTH);
        jb = new JButton("Send");
        jp.add(jb, BorderLayout.EAST);
        jtf = new JTextField("");
        jp.add(jtf, BorderLayout.CENTER);

        jtf.setBackground(Color.DARK_GRAY);
        jtf.setForeground(Color.LIGHT_GRAY);
        jta.setBackground(Color.DARK_GRAY);
        jta.setForeground(Color.LIGHT_GRAY);
        jb.setBackground(Color.DARK_GRAY);
        jb.setForeground(Color.GRAY);

        jp.grabFocus();

        jb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!jtf.getText().trim().isEmpty()) {
                    System.out.println("Your message: " + jtf.getText());
                    sendMsg();
                    jtf.setText("");
                }
            }
        });

        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jtf.getText().trim().isEmpty()) {
                    System.out.println("Your message: " + jtf.getText());
                    sendMsg();
                    jtf.setText("");
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        if(scanner.hasNext()){
                            String msg = scanner.nextLine();
                            if(msg.equalsIgnoreCase("End session")) break;
                            jta.append(msg);
                            jta.append("\n");
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        setVisible(true);
    }

    void sendMsg() {

        ptw.println(jtf.getText());
        jta.append(getTime() + ": ");
        ptw.flush();
        jtf.setText("");
        jtf.grabFocus();
    }

    String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

}

