package stc06.gubarkov.groupChat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Запустить клиента (C) или сервера (S)?");
        System.out.println("Или завершить выполнение программы (E)?");
        char choice = Character.toLowerCase(scanner.nextLine().charAt(0));
        switch(choice) {
            case 'c':
                System.out.println("Введите номер порта сервера:");
                new ChatClient(scanner.nextLine());
                break;
            case 's':
                System.out.println("Введите номер порта сервера:");
                new ChatServer(scanner.nextLine());
                break;
            case 'e':
                System.exit(0);
            default:
                System.out.println("Повторите ввод");
        }
    }
}
