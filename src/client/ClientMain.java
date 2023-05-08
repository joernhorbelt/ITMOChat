package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class ClientMain extends Application {
    static String username;
    static VBox chat = new VBox();
    static VBox userList = new VBox();
    public static void main(String[] args) {
        launch(args);
        //Application.launch(Main.class, args);

    }
    public void start(Stage primaryStage) throws Exception {
        /*BorderPane root = new BorderPane();
        root.setMinSize(1000,700);
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
        Scene scene = new Scene(root, 1000,700);*/
        Scene loginScene = new Scene(createLoginPane(primaryStage));



        primaryStage.setScene(loginScene);
        primaryStage.show();
        new Thread(() ->{
            try {
                Socket socket = new Socket("localhost", 5002);
                BufferedReader userListReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String username = userListReader.readLine();
                    System.out.println("username tralalal");
                    Platform.runLater(() -> {
                        // adds the received message to a JavaFX GUI element
                        userList.getChildren().add(new Text(username));
                    } );
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }

        });
        /*new Thread(() -> {
            try {
                Socket loginsocket = new Socket("localhost", 5001);
                PrintWriter output = new PrintWriter(loginsocket.getOutputStream(), true);
                Scanner sc = new Scanner(System.in);
                System.out.println("username;password");
                String login = sc.nextLine();
                output.println(login);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }

        }).start();*/

            /*new Thread(() ->{
                try {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Username eingeben");
                    String username= (sc.nextLine());
                    Platform.runLater(() -> {
                        //userList.getChildren().add(new Text(username));
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
            }).start();*/


    }

    public static void createMessageTextBox(String message) {
        chat.getChildren().add(new Text(message));
        System.out.println(message+ "test erfolgreich");

    }
    // Create the registration pane and return it
    public GridPane createRegisterGridPane(Stage primaryStage, Scene loginScene) {
        primaryStage.setTitle("Registrieren");
        // Create the registration form elements
        Button buttonRegister = new Button("Registrieren");
        Button buttonRegistriert = new Button("zum Login");
        Label regUsername = new Label("Name");
        Label regPassword = new Label("Password");
        TextField regUserTextfield = new TextField();
        PasswordField regPassTextfield = new PasswordField();

        // Create the registration pane and set its properties
        GridPane gridPaneRegister = new GridPane();
        gridPaneRegister.setMinSize(400, 200);
        gridPaneRegister.setHgap(5);
        gridPaneRegister.setVgap(5);
        gridPaneRegister.setAlignment(Pos.CENTER);

        // Add the registration form elements to the pane
        gridPaneRegister.add(regUsername, 0, 0);
        gridPaneRegister.add(regUserTextfield, 1, 0);
        gridPaneRegister.add(regPassword, 0, 1);
        gridPaneRegister.add(regPassTextfield, 1, 1);
        gridPaneRegister.add(buttonRegister, 0, 2);
        gridPaneRegister.add(buttonRegistriert, 1, 2);

        // Set action for the registration button click
        buttonRegister.setOnMouseClicked(e -> {
            // Get the username and password entered by the user
            String username = regUserTextfield.getText();
            String password = regPassTextfield.getText();

            primaryStage.setTitle("Login");
            primaryStage.setScene(loginScene);
        });

        // Button click event handler for buttonRegistriert
        buttonRegistriert.setOnMouseClicked(e -> {
            primaryStage.setTitle("Login");
            primaryStage.setScene(loginScene);
        });

        return gridPaneRegister;
    }

    public BorderPane chatWindowPane(Stage primaryStage){
        BorderPane root = new BorderPane();
        root.setMinSize(1000,700);
        HBox header= new HBox();
        Text title = new Text("ITMO Chat");
        title.setTextAlignment(TextAlignment.CENTER);
        header.getChildren().add(title);
        //VBox userList = new VBox();
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
        try {
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

        return root;
    };

    public GridPane createLoginPane(Stage primaryStage) {

        // Create UI elements
        Button submitButton = new Button("Submit");
        Button clearButton = new Button("Clear");
        Button zurueckButton = new Button("zurück");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();

        // Create grid panel
        GridPane gridPaneLogin = new GridPane();

        // Set size of grid panel
        gridPaneLogin.setMinSize(400, 200);

        // Set gap between grid elements
        gridPaneLogin.setHgap(5);
        gridPaneLogin.setVgap(5);

        // Set grid alignment
        gridPaneLogin.setAlignment(Pos.CENTER);

        // Add UI elements to grid panel
        gridPaneLogin.add(usernameLabel, 0, 0);
        gridPaneLogin.add(usernameTF, 1, 0);
        gridPaneLogin.add(passwordLabel, 0, 1);
        gridPaneLogin.add(passwordTF, 1, 1);
        gridPaneLogin.add(submitButton, 0, 2);
        gridPaneLogin.add(clearButton, 1, 2);
        gridPaneLogin.add(zurueckButton, 2, 2);

        // Event handler for submit button
        submitButton.setOnMouseClicked(f -> {
            // Get username from text field
            Socket socket = null;
            try {
                socket = new Socket("localhost", 5001);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String loginData = usernameTF.getText() + ";" + passwordTF.getText();
                username = usernameTF.getText();
                out.println(loginData);
                System.out.println(loginData);
                Scene root = new Scene(chatWindowPane(primaryStage));
                primaryStage.setScene(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        });

        // Event handler for clear button
        clearButton.setOnMouseClicked(f -> {
            // Clear the text fields
            usernameTF.clear();
            passwordTF.clear();
        });

        // Event handler for "zurück" button
        zurueckButton.setOnMouseClicked(f -> {
            try {
                start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return gridPaneLogin;
    }

}
