package server;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginServer{

    public static void main(String[] args) {
        //mysql database connection details
        String url = "jdbc:mysql://localhost:3306/chatMember";
        String user = "root";
        String pass = "";
        try {
            //new serversocket to accept login requests
            ServerSocket loginServer = new ServerSocket(5001);
            Socket socket = loginServer.accept();
            //inputstream reader that recieves login data from clients
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter loginOut = new PrintWriter(socket.getOutputStream(), true);
            //connect to mysql database
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stm = con.createStatement();
            // Connect to database

            while (true) {
                try {
                    //connect to mysql database

                    String userNamePassword = input.readLine();
                    //format of input : "username;password"
                    String[] userData = userNamePassword.split(";");
                    String name =  userData[0];
                    String password = userData[1];
                    ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM tbl_member WHERE username = '" + name + "' AND password = '" + password + "'");
                    rs.next();
                    int count = rs.getInt(1);
                    if (count > 0) {
                        //login data correct. succesful login
                        System.out.println("login succesful");
                        loginOut.println(name);
                    } else {
                        System.out.println("Invalid username or password");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
