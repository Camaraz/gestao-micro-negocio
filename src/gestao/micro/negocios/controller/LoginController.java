package gestao.micro.negocios.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.UserDAO;
import gestao.micro.negocios.model.User;
import javafx.scene.control.Alert;

/**
 *
 * @author upper
 */
public class LoginController {
    private UserDAO dataAccessor ;
    
    @FXML
    private TextField user;
    @FXML
    private TextField password;

    private MainApp mainApp;
    

    @FXML
    private void handleEntrar() throws Exception {
        User login = UserDAO.getInstance().Login(user.getText(), password.getText());
        if( login != null ){
            mainApp.setIdUser(login.getId());
            mainApp.initMain();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dados inválidos");
            alert.setHeaderText("Login inválido");
            alert.setContentText("Por favor, verifique os dados novamente.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRegister() {
        mainApp.showRegister();
    }

    @FXML
    private void handleLimpar() {
        user.setText("");
        password.setText("");
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
