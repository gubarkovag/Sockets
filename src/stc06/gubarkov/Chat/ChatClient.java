package stc06.gubarkov.Chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by admin on 15.06.2017.
 */
public class ChatClient {
    static int serverPort = 7777;
    static String address = "127.0.0.1";
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(address, serverPort);
            System.out.println("I am gonna interact with server");

            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream in = new DataInputStream(socketInputStream);
            DataOutputStream out = new DataOutputStream(socketOutputStream);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String messageText;

            while(true) {
                messageText = keyboard.readLine();
                System.out.println("Send to server: " + messageText);
                out.writeUTF(messageText);
                out.flush();
                messageText = in.readUTF();
                System.out.println("Answer from server: " + messageText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
