package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 */
/*
    This class listens to for incoming client socket requests. When connected, a new CommansHandler class is created
    which takes care of the client command. A new CommandHandler is created for every command sent by the client.
 */

public class ClientConnectionHandler implements Runnable {

    private ServerSocket serverSocket;

    public ClientConnectionHandler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        while (true) {
            try {
                System.out.print("Waiting for command from client.");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Command received");
                CommandHandler handler = new CommandHandler(clientSocket);
                Thread handlerThread = new Thread(handler);
                handlerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}