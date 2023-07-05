package server;

import java.io.BufferedReader;
import java.io.IOException;
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
                    System.out.println(socket.getRemoteSocketAddress());
                    ServerThread serverThread = new ServerThread(socket, threadList);
                    threadList.add(serverThread);
                    serverThread.start();
                }
            } catch (Exception e) {
                System.out.println("Error occured in main of server : "+ e.getStackTrace());
            }
        }).start();
        new Thread(() -> {
            try (ServerSocket signupServerSocket = new ServerSocket(5002)) {
                while (true) {
                    Socket signupSocket = signupServerSocket.accept();
                    SignupThread signupThread = new SignupThread(signupSocket);
                    signupThread.start();
                }
            } catch (IOException e) {
                System.out.println("Fehler im signupserver"+e.getStackTrace());
            }
        }).start();
    }
}
