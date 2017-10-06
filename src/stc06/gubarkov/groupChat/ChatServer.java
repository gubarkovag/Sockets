package stc06.gubarkov.groupChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {
    private ServerSocket serverSocket;
    private List<ChatClient> chatClients = new ArrayList<>();
    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());

    public ChatServer(String port) {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));

            while (true) {
                Socket socket = serverSocket.accept();
                Connection conn = new Connection(socket);
                connections.add(conn);
                Thread th = new Thread(conn);
                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    private void closeAll() {
        try {
            serverSocket.close();

            synchronized (connections) {
                for (Connection conn : connections) {
                    conn.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Потоки не были закрыты корректно");
        }
    }

    private class Connection implements Runnable {
        private DataInputStream in;
        private DataOutputStream out;
        private Socket socket;

        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                String clientName = in.readUTF();
                synchronized(connections) {
                    for (Connection conn : connections) {
                        conn.out.writeUTF(clientName + " comes now");
                    }
                }
                while (true) {
                    try {
                        String inputLine = in.readUTF();
                        if (inputLine.equals("exit")) {
                            break;
                        }

                        synchronized (connections) {
                            for (Connection conn : connections) {
                                conn.out.writeUTF(clientName + ": " + inputLine);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (connections) {
                    for (Connection conn : connections) {
                        conn.out.writeUTF(clientName + " has left");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void close() {
            try {
                in.close();
                out.close();
                socket.close();

                connections.remove(this);
                if (connections.size() == 0) {
                    ChatServer.this.closeAll();
                    System.exit(0);
                }
            } catch (IOException e) {
                System.out.println("Потоки не были закрыты корректно");
            }
        }
    }
}
