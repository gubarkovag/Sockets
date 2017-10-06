package stc06.gubarkov.groupChat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import static stc06.gubarkov.serverconsts.ServerConsts.*;

public class ChatClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ChatClient(String serverPort) {
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket(SERVERADDRESS, Integer.parseInt(serverPort));
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
