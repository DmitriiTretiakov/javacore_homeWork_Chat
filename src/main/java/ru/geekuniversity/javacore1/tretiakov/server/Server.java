package ru.geekuniversity.javacore1.tretiakov.server;

import ru.geekuniversity.javacore1.tretiakov.ServerConst;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements ServerConst {

    private ServerSocket server;
    private Vector< ClientHandler > clients;
    private AuthService authService;
    public AuthService getAuthService(){
        return authService;
    }

    public Server(){
        ServerSocket serverSocket = null;
        Socket socket = null;
        clients = new Vector<>();
        try{
            serverSocket = new ServerSocket(PORT);
            authService = new BaseAuthService();
            authService.start(); //placeholder
            System.out.println("The server is running ...");
            while(true){
                socket = serverSocket.accept();
                clients.add(new ClientHandler(this, socket));
                System.out.println("Client connected");
            }
        }catch(IOException e){
            System.out.println("Initialization error");
        }finally{
            try{
                serverSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void broadcast(String message){
        for(ClientHandler client : clients){
            client.sendMessage(message);
        }
    }

    public void sendMessageTo(String nick, String message){
        for(ClientHandler client : clients){
            if (nick.equalsIgnoreCase(client.getNick())) {
                client.sendMessage(message);
            }
        }
    }

    public void unsubscribeMe(ClientHandler c){
        clients.remove(c);
    }

}
