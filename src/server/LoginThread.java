package server;
//test
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

public class LoginThread extends Thread {
    private final Socket socket;
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
               /* String username = input.readLine();
                String password = input.readLine();*/
                String[] usernamePassword = userInput.split(";");
                String username = usernamePassword[0];
                String password = usernamePassword[1];
                //convert password to sha3-256 hash
                //final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
                /*final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                String sha3Hex = bytesToHex(hashbytes);*/

                ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM tbl_member WHERE username = '" + username + "' AND password = '" + password + "'");
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    //login data correct. successful login
                    //Socket socketLoginToServer = new Socket("192.168.13.123", 5002);
                    //PrintWriter outToUserlist = new PrintWriter(socketLoginToServer.getOutputStream(), true);
                    System.out.println("login successful");
                    output.println("success");
                    //outToUserlist.println(username);
                } else if(count==0) {
                    output.println("Invalid username or password");
                    System.out.println("Invalid username or password");
                }
            }

        } catch (Exception e) {
            System.out.println("Error"+e.getStackTrace());
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
