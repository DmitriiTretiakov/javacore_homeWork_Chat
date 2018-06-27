package ru.geekuniversity.javacore1.tretiakov.server;

import java.util.ArrayList;

public class BaseAuthService implements AuthService {

    private class Entry {
        private String login;
        private String pass;
        private String nick;
        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    private ArrayList<Entry>entries;
    @Override
    public void start() { }
    @Override
    public void stop() { }

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("Dima","pass1","Dima"));
        entries.add(new Entry("Natarinka","pass2","Natarinka"));
        entries.add(new Entry("Kaban","pass3","Kaban"));
    }

    @Override
    public String getNickByLoginPass( String login , String pass) {
        for( Entry o : entries) {
            if(o.login.equals(login) && o.pass.equals(pass)) return o.nick;
        }
        return null;
    }
}
