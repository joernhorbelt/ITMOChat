import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.awt.*;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;

public class Main extends Application {
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
        ScrollPane scrollPane = new ScrollPane();

        VBox chat = new VBox();
        chat.maxHeight(600);
        chat.setBorder(border);

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
        root.setCenter(chat);
        root.setStyle("-fx-font-family: " +
                "'Lucida Sans Unicode'");
        root.setStyle("-fx-font-size: 18");
        Scene scene = new Scene(root, 1000,700);
        String username = "bömi";
        String message = "Huhuuu";
        submit.setOnAction(e -> {
            if (userInput.getText().equals("")) {
                System.out.println("No input");
                userInput.requestFocus();
            } else {
                System.out.println(userInput.getText());
                chat.getChildren().add(new Text(username + " : " + userInput.getText()));
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
                    chat.getChildren().add(new Text(username + " : " + userInput.getText()));
                    userInput.setText("");
                    userInput.requestFocus();
                }
            }
        });






        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}