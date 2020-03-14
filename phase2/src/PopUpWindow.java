import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * This was adapted from the YouTube JavaFX teaching video by thenewboston;
 * URL is https://youtu.be/SpL3EToqaXA
 * */
public class PopUpWindow {
    static boolean answer;

    public static void popUp(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMaxHeight(500);

        Label label = new Label(message);
        label.setAlignment(Pos.CENTER);
        Button close = new Button("Close");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
    static boolean confirmationBox(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMaxHeight(300);

        Label label = new Label(message);
        label.setAlignment(Pos.CENTER);
        Button yes = new Button("Yes");
        Button no = new Button("No");
        yes.setOnAction(e -> {answer = true; window.close();});
        no.setOnAction(e -> {answer = false; window.close();});

        HBox choiceLayout = new HBox(20);
        choiceLayout.getChildren().addAll(yes, no);
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10,10,10,10));
        choiceLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, choiceLayout);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;

    }
}
