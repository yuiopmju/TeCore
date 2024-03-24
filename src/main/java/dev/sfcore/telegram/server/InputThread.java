package dev.sfcore.telegram.server;

import java.util.Scanner;

public class InputThread extends Thread {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine();
            OutputThread.addCommand(input);
        }
    }
}
