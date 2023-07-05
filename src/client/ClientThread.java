package client;

import javafx.application.Platform;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class ClientThread extends Thread {
    // Declares private instance variables
    // stores the socket associated with this thread
    private Socket socket;
    // stores a BufferedReader to read data from the socket
    private BufferedReader input;
    // Constructor to initialize the instance variables
    public ClientThread(Socket s) throws Exception {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Overrides the run() method of the Thread class
    @Override
    public void run() {
        try {
// Continuously reads input from the socket until an exception is thrown or the thread is interrupted
            while (true) {
                // reads a line of input from the BufferedReader
                String response = input.readLine();
                // prints the received message to the console
                System.out.println(response);
                // runs the following code on the JavaFX application thread
                Platform.runLater(() -> {
                    // adds the received message to a JavaFX GUI element
                    ClientMain.chat.getChildren().add(new Text(response));
                } );
            }
            // catches an IOException thrown while reading input from the socket
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // closes the BufferedReader used to read input from the socket
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
