package ru.geekuniversity.javacore1.tretiakov.server;

public interface AuthService {
    void start ();
    void stop();
    String getNickByLoginPass (String login ,String pass);
}

