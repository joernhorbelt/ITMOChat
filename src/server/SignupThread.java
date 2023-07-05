package server;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

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
                String[] userNamePassword = userInput.split(";");
                String username = userNamePassword[0];
                String password = userNamePassword[1];
                ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM tbl_member WHERE username = '" + username + "'");
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("Benutzername bereits vorhanden");
                    output.println("Benutzername bereits vorhanden");
                } else if (count == 0) {
                    System.out.println("Registration Successful");
                    output.println("Registration successful");
                    try {
                        //final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
                        //final byte[] hashbytes = digest.digest(
                          //      password.getBytes(StandardCharsets.UTF_8));
                        //String sha3Hex = bytesToHex(hashbytes);
                        String insertInto = "Insert into tbl_member (username,password) values(?,?)";
                        PreparedStatement statement = con.prepareStatement(insertInto);
                        statement.setString(1,username);
                        statement.setString(2,password);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler im SignUp Server"+ e.getStackTrace());
        }
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
