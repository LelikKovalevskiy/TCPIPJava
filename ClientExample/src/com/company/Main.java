package com.company;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintStream;
        import java.net.Socket;
        import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        byte[] byteIp=new byte[4];
        String msg;
        String ipString[];
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите IP  сервера...");
        msg=scanner.nextLine();
        try {

            socket = new Socket(msg, 8044);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            msg = br.readLine();
            System.out.println(msg + "\nЖдите хода соперника...");

            while (true) {

                msg = br.readLine();
                if (msg.contains("выигрыш")) {
                    System.out.print(msg);
                    break;
                }

                System.out.print("Лента:" + msg + "Ваш ход:\n<");
                while (true) {
                    msg = scanner.nextLine();
                    if (msg.equals("r") || msg.equals("l"))
                        break;
                }
                ps.flush();
                System.out.println("\nЖдите хода соперника...");

            }
            ps.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}