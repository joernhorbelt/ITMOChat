package server;

import client.ClientMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/*
This is the beginning of the ServerThread class. It is in the server package.
It imports the necessary classes to set up a socket-based server.
It extends the Thread class, so it is a separate thread of execution from the main thread of the program.
It has instance variables socket, threadList, and output, which will be initialized in the constructor.
 */
public class ServerThread extends Thread {

    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    /*
    This is the run() method, which is called when the thread starts.
    It sets up a BufferedReader and a PrintWriter to communicate with the client through the socket.
    It enters an infinite loop to keep receiving messages from the client.
    If the message is "exit", it breaks out of the loop and closes the thread.
    Otherwise, it calls the printToAllClients() method to send the message to all clients connected to the server.
    It also prints the message received to the server console.
     */
    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String outputString = input.readLine();
                if (outputString.equals("exit")) {
                    break;
                }
                printToAllClients(outputString);
                System.out.println("Server received " + outputString);
            }
        } catch (Exception e) {
            System.out.println("Error occurred in main of server"+ e.getStackTrace());
        }
    }
/*
This is the printToAllClients() method.
It takes a String as input and sends it to all clients connected to the server.
It does this by iterating through the threadList, which contains all the threads
of connected clients,
 and calling their output.println() methods to send the message.
 */
    private void printToAllClients(String outputString) {
        for (ServerThread serverThread : threadList) {
            serverThread.output.println(outputString);
        }
    }
}
