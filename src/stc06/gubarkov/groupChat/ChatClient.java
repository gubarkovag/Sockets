package stc06.gubarkov.groupChat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final int serverPort = 7777;
    private final String address = "127.0.0.1";

    public ChatClient() {
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket(address, serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Введите свой ник:");
            out.writeUTF(scanner.nextLine());

            Resender resend = new Resender();
            Thread th = new Thread(resend);
            th.start();

            String str = "";
            while (!str.equals("exit")) {
                str = scanner.nextLine();
                out.writeUTF(str);
            }
            resend.setStopped(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Resender implements Runnable {
        private boolean stopped;

        public void setStopped(boolean stopped) {
            this.stopped = stopped;
        }

        @Override
        public void run() {
            while(!stopped) {
                try {
                    String inputLine = in.readUTF();
                    System.out.println(inputLine);
                } catch (IOException e) {
                    System.out.println("Ошибка при получении сообщения");
                    e.printStackTrace();
                }
            }
            System.exit(0);
        }
    }
}
