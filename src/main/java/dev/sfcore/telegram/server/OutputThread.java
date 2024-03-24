package dev.sfcore.telegram.server;

import dev.sfcore.telegram.Launcher;
import dev.sfcore.telegram.TeKit;

import java.util.concurrent.BlockingQueue;

public class OutputThread extends Thread {

    private static BlockingQueue<String> commandQueue;
    private boolean promptNeeded = true;

    @Override
    public void run() {
        try {
            while (true) {
                if (promptNeeded) {
                    System.out.print("> ");
                    promptNeeded = false;
                }

                String command = commandQueue.poll();
                if (command != null) {
                    execute(command);
                }

                Thread.sleep(200);

                if (command != null && command.isEmpty()) {
                    promptNeeded = true;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCommand(String command){
        try {
            commandQueue.put(command);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void execute(String cmd){
        if(cmd.equals("end")){
            System.out.println(TeKit.GREEN + "Disable response sent" + TeKit.RESET);
            Launcher.shutdown();
        } else {
            System.out.println(TeKit.RED + "Unknown command" + TeKit.RESET);
        }
    }

    public OutputThread(BlockingQueue<String> queue) {
        commandQueue = queue;
    }
}
