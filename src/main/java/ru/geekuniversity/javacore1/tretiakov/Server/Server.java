package ru.geekuniversity.javacore1.tretiakov.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    Scanner scanner;
    PrintWriter ptw;
    ServerSocket server = null;
    Socket sock = null;

    public Server() {

        try {
            server = new ServerSocket ( 8189 );
            System.out.println("Server started...");
            sock = server.accept();
            scanner = new Scanner(sock.getInputStream());
            ptw = new PrintWriter(sock.getOutputStream());
            while (true) {
                String str = scanner.nextLine();
                if (str.equals("end")) break ;
                ptw.println("Эхо: " + str);
                ptw.flush();
            }
        } catch (IOException e) {
            System.out.println("Server initialization error");
        } finally {
            try {
                sock.close();
                server.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
