package stc06.gubarkov.groupChat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Запустить клиента (C) или сервера (S)?");
        while (true) {
            char choice = Character.toLowerCase(scanner.nextLine().charAt(0));
            switch(choice) {
                case 'c':
                    new ChatClient();
                    break;
                case 's':
                    new ChatServer();
                    break;
                default:
                    System.out.println("Повторите ввод");
            }
        }
    }
}
