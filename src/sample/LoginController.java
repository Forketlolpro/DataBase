package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * Created by ilya on 09.05.2017.
 */
public class LoginController {
    @FXML
    Button buttonEnter;
    @FXML
    TextField textFieldLogin;
    @FXML
    TextField textFieldPassword;

    @FXML
    public void initialize(){
        buttonEnter.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String login="boss";
            String password="12345";
            //if (textFieldLogin.getText().equals(login) && textFieldPassword.getText().equals(password)){
                Main.stage.setTitle("База данных");
                Main.stage.setScene(Main.workScene);
                Main.stage.show();
           // }

        });
    }
}
