package ru.geekuniversity.javacore1.tretiakov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientWindow extends JFrame {

    JTextArea jta;
    JTextField jtf;
    JButton jb;
    JPanel jp;
    JFrame frame;

    public  ClientWindow (){
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
                    sendMsg();
                    System.out.println("Your message: " + jtf.getText());
                    jtf.setText("");
                }
            }
        });

        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!jtf.getText().trim().isEmpty()) {
                    sendMsg();
                    System.out.println("Your message: " + jtf.getText());
                    jtf.setText("");
                }
            }
        });

        setVisible(true);
    }

    void sendMsg() {
        String out = jtf.getText();
        jta.append(getTime() + ": " + out + "\n");
        jtf.grabFocus();
    }

    String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
}

