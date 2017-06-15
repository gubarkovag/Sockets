package stc06.gubarkov.groupChat;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer implements Runnable {
    private ServerSocket ss;
    private int port;

    public ChatServer(int port) throws IOException {
        ss = new ServerSocket(port);
        this.port = port;
    }

    @Override
    public void run() {
        while(true) {

        }
    }
}
