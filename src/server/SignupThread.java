package server;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignupThread extends Thread {
    private Socket socket;
    private final String url = "jdbc:mysql://localhost:3306/chatMember";
    private final String user = "root";
    private final String pass = "";

    public SignupThread(Socket socket) {
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
                String userInput = input.readLine();
                System.out.println(input);
                String[] userNamePassword = userInput.split(";");
                String username = userNamePassword[0];
                String password = userNamePassword[1];
                ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM tbl_member WHERE username = '" + username + "'");
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("Benutzername bereits vorhanden");
                    output.println("Username is already in use.");
                } else if (count == 0) {
                    System.out.println("Registration Successful");
                    output.println("Registration successful");
                    stm.executeQuery("INSERT INTO tbl_member values (null, '" + username + "," + password + "')");

                }
            }
        } catch (Exception e) {
            System.out.println("Fehler im SignUp Server"+ e.getStackTrace());
        }
    }

}
