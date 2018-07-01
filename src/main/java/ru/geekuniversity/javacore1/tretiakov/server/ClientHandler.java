package ru.geekuniversity.javacore1.tretiakov.server;

import ru.geekuniversity.javacore1.tretiakov.ServerAPI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements ServerAPI {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private static final String UNDEFINED = "undefined";

    public ClientHandler(Server server, Socket socket){
        try{
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            this.nick = UNDEFINED;
        }catch(IOException e){
            e.printStackTrace();
        }

        new Thread(()-> {
            try{
                while(true){
                    String message = in.readUTF();
                    if(message.startsWith(AUTH)){
                        String[] elements = message.split(" ");
                        String nick = server.getAuthService().getNickByLoginPass(elements[1], elements[2]);
                        if(nick != null){
                            sendMessage(AUTH_SUCCESSFUl + " " + nick);
                            this.nick = nick;
                            server.broadcast(this.nick + " has entered the chat room");
                            break;
                        } else sendMessage("Wrong login/password!");
                        disconnectAfterTime();
                    } else sendMessage("This account is already in use!");
                    disconnectAfterTime();
                }

                while(true){
                    String message = in.readUTF();
                    if(message.startsWith(SYSTEM_SYMBOL)){
                        if(message.equalsIgnoreCase(CLOSE_CONNECTION))
                            break;
                        else if(message.startsWith(PRIVATE_MESSAGE)){
                            String nameTo = message.split(" ")[1];
                            String messageText = message.substring(PRIVATE_MESSAGE.length() + nameTo.length() + 2);
                            server.sendPrivateMessage(this, nameTo, messageText);
                        }else sendMessage("Command doesn't exist!");
                    }else {
                        System.out.println("client " + message);
                        server.broadcast(getTime() + ":  " + this.nick + ": " + message);
                    }
                }
            }catch(IOException e){
            }finally{
                disconnect();
            }
        }).start();
    }

    public void sendMessage(String msg){
        try{
            out.writeUTF(msg);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        sendMessage(CLOSE_CONNECTION + " You have been disconnected!");
        server.unsubscribeMe(this);
        try{
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void disconnectAfterTime(){
        new Thread(()-> {
            try{ Thread.sleep(120000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(nick.equals(UNDEFINED)){
                disconnect();
            }
        }).start();
    }

    public String getNick() {
        return nick;
    }

    String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
}