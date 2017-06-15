package stc06.gubarkov.Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    static int port = 7777;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for a client");
            Socket socket = serverSocket.accept();
            System.out.println("Have a client");

            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream in = new DataInputStream(socketInputStream);
            DataOutputStream out = new DataOutputStream(socketOutputStream);

            String messageText;
            while(true) {
                messageText = in.readUTF();
                System.out.println("Got from client: " + messageText);
                System.out.println("Send to client: " + messageText);
                out.writeUTF(messageText);
                out.flush();
            }

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
