package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println(InetAddress.getLocalHost());
            Random random = new Random();
            tape = new Vector<Integer>();
            for (int i = 0; i < 6; ++i)
                tape.add(random.nextInt(50) + 50);

            ServerSocket server = new ServerSocket(8044);
            socket = server.accept();
            PrintStream ps = new PrintStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String msg = "Привет! Правила правила игры: Игрокам даётся \"лента\" с цифрами. От правого или левого конца каждый игрок отрывает по очереди одну цифру." +
                    "В конце игры, когда лента иссякнет, побеждает тот, чья сумма оторванных чисел больше. Оторвать левую/правую часть: l/r  ";
            System.out.print(msg + "\n");
            ps.println(msg);
            ps.flush();

            while (!tape.isEmpty()) {
                System.out.print("Лента: " + tape.toString() + " Ваш ход:\n<");
                while (true) {
                    msg = scanner.nextLine();
                    if (msg.equals("r") || msg.equals("l"))
                        break;
                }
                if (msg.equals("l"))
                    score1 += tape.remove(0);
                else if (msg.equals("r"))
                    score1 += tape.remove(tape.size() - 1);
                System.out.println("\nЖдите хода соперника...");
                ps.println(tape.toString());
                ps.flush();
                msg = br.readLine();
                if (msg.equals("l"))
                    score2 += tape.remove(0);
                else if (msg.equals("r"))
                    score2 += tape.remove(tape.size() - 1);
            }
            System.out.print("Ваш выигрыш: " + Integer.toString(score1));
            if (score1 > score2) {
                System.out.print("- Вы победили!");
                ps.print("Ваш выигрыш: " + Integer.toString(score2) + " - Вы проиграли!");
            } else if (score1 < score2) {
                System.out.print("- Вы проиграли!!");
                ps.print("Ваш выигрыш: " + Integer.toString(score2) + " - Вы победили!");
            } else {
                System.out.print("- Ничья!!");
                ps.print("Ваш выигрыш: " + Integer.toString(score2) + " - Ничья!");
            }
            ps.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("error" + e);

        } finally {
            if (socket != null)
                socket.close();
        }
    }

    private static Vector<Integer> tape;
    private static int score1 = 0;
    private static int score2 = 0;

}
