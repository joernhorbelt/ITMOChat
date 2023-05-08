package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<ServerThread> threadList =new ArrayList<>();
        new Thread(() -> {
            try (ServerSocket loginServerSocket = new ServerSocket(5001)) {
                while (true) {
                    Socket loginSocket = loginServerSocket.accept();
                    LoginThread loginThread = new LoginThread(loginSocket);
                    loginThread.start();
                }
            } catch (Exception e) {
                System.out.println("Error occured in Loginserver : "+ e.getStackTrace());
            }
        }).start();

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(5000)){
                while (true) {
                    Socket socket = serverSocket.accept();
                    ServerThread serverThread = new ServerThread(socket, threadList);
                    threadList.add(serverThread);
                    serverThread.start();
                }
            } catch (Exception e) {
                System.out.println("Error occured in main of server : "+ e.getStackTrace());
            }
        }).start();
    }
}
