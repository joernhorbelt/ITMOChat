package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class ClientMain extends Application {
    static VBox chat = new VBox();
    public static void main(String[] args) {
        launch(args);
        //Application.launch(Main.class, args);

    }
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        HBox header= new HBox();
        Text title = new Text("ITMO Chat");
        title.setTextAlignment(TextAlignment.CENTER);
        header.getChildren().add(title);
        VBox userList = new VBox();
        Border border = new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        userList.setBorder(border);
        //toolbar.setBorder(border);
        Text textField = new Text("Bömi");

        userList.getChildren().addAll(textField);
        userList.setPrefWidth(200);



        ScrollPane scrollPane = new ScrollPane(chat);
        scrollPane.vvalueProperty().bind(chat.heightProperty());


        HBox input = new HBox();
        input.setBorder(border);
        input.setPrefWidth(1000);
        TextField userInput = new TextField();
        userInput.setPrefWidth(800);
        userInput.maxWidth(800);

        input.getChildren().add(userInput);
        Button submit = new Button("Submit");
        input.getChildren().add(submit);
        root.setTop(header);
        root.setRight(userList);
        root.setBottom(input);
        root.setCenter(scrollPane);
        //root.setCenter(chat);
        root.setStyle("-fx-font-family: " +
                "'Lucida Sans Unicode'");
        root.setStyle("-fx-font-size: 18");
        Scene scene = new Scene(root, 1000,700);



        primaryStage.setScene(scene);
        primaryStage.show();

            new Thread(() ->{
                try {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Username eingeben");
                    String username= (sc.nextLine());
                    Platform.runLater(() -> {
                        userList.getChildren().add(new Text(username));
                    });

                    Socket socket = new Socket("localhost", 5000);
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    ClientThread clientThread = new ClientThread(socket);
                    clientThread.start();
                    submit.setOnAction(e -> {
                        if (userInput.getText().equals("")) {
                            System.out.println("No input");
                            userInput.requestFocus();
                        } else {
                            //message.set(userInput.getText());
                            System.out.println(userInput.getText());
                            String message = userInput.getText();
                            output.println(username+ ": "+message);
                            //test.getChildren().add(new Text(username + " : " + message));
                            userInput.setText("");
                            userInput.requestFocus();
                        }
                    });

                    userInput.setOnKeyPressed(ke -> {
                        if (ke.getCode().equals(KeyCode.ENTER)) {
                            if (userInput.getText().equals("")) {
                                System.out.println("No input");
                                userInput.requestFocus();
                            } else {
                                System.out.println(userInput.getText());
                                //message.set(userInput.getText());
                                String message = userInput.getText();
                                output.println(username+ ": "+message);
                                //test.getChildren().add(new Text(username + " : " + message));
                                userInput.setText("");
                                userInput.requestFocus();
                            }
                        }
                    });
                } catch (Exception e) {
                    System.out.println("Exception in client main" + e.getStackTrace());
                }
            }).start();


    }

    public static void createMessageTextBox(String message) {
        chat.getChildren().add(new Text(message));
        System.out.println(message+ "test erfolgreich");

    }
}
