package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
    A server is started once this file is executed. A ClientConnectionHandler is created which is an infinite loop
    that listens for further sockets looking to connect.
 */

public class Server {
    private ServerSocket serverSocket = null;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRequest() throws IOException{
        ClientConnectionHandler handler = new ClientConnectionHandler(this.serverSocket);
        Thread handlerThread = new Thread(handler);
        handlerThread.start();
    }
}
