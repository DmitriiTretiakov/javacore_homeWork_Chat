package ru.geekuniversity.javacore1.tretiakov.client;


import ru.geekuniversity.javacore1.tretiakov.ServerAPI;
import ru.geekuniversity.javacore1.tretiakov.ServerConst;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements ServerConst, ServerAPI {
    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    private boolean isAuthrozied = false;
    public boolean isAuthrozied(){
        return isAuthrozied;
    }

    public void setAuthrozied(boolean authrozied){
        isAuthrozied = authrozied;
    }

    public ClientConnection(){ }

    public void init(ClientWindow view){
        try{
            this.socket = new Socket(SERVER_URL, PORT);
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
            new Thread(()-> {
                try{
                    while(true){
                        String message = in.readUTF();
                        if(message.startsWith(AUTH_SUCCESSFUl)){
                            setAuthrozied(true);
                            view.switchWindows();
                            break;
                        }
                        view.showMessage(message);
                    }
                    while(true){
                        String message = in.readUTF();
                        String[] elements = message.split(" ");
                        if(message.startsWith(SYSTEM_SYMBOL)){
                            if(elements[0].equals(CLOSE_CONNECTION)) {
                                setAuthrozied(false);
                                view.showMessage(message.substring(CLOSE_CONNECTION.length() + 1));
                                view.switchWindows();
                            }
                        }else{
                            view.showMessage(message);
                        }
                    }
                }catch(IOException e){

                }
            }).start();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try{
            out.writeUTF(message);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void auth(String login, String password){
        try{
            out.writeUTF(AUTH + " " + login + " " + password);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}