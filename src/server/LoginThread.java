package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

public class LoginThread extends Thread {
    private Socket socket;
    private final String url = "jdbc:mysql://localhost:3306/chatMember";
    private final String user = "root";
    private final String pass = "";

    public LoginThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stm = con.createStatement();
            while (true) {
                //String format "username;password"
                String userInput = input.readLine();
                System.out.println(userInput);
                String[] usernamePassword = userInput.split(";");
                String username = usernamePassword[0];
                String password = usernamePassword[1];
                ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM tbl_member WHERE username = '" + username + "' AND password = '" + password + "'");
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    //login data correct. successful login
                    Socket socketLoginToServer = new Socket("localhost", 5002);
                    PrintWriter outToUserlist = new PrintWriter(socketLoginToServer.getOutputStream(), true);
                    System.out.println("login successful");
                    output.println(username);
                    outToUserlist.println(username);
                } else {
                    System.out.println("Invalid username or password");
                }
            }

        } catch (Exception e) {
            System.out.println("Error"+e.getStackTrace());
        }
    }
}
