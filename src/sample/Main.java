package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage stage;
    static Scene loginScene;
    static Scene workScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        stage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        workScene = new Scene(root,933,553);
        root = FXMLLoader.load(getClass().getResource("loginSample.fxml"));
        loginScene = new Scene(root,237,142);
        stage.setTitle("Вход");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
