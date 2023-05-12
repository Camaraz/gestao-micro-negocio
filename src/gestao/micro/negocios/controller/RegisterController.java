package gestao.micro.negocios.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.UserDAO;
import java.io.File;
import java.io.FileInputStream;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author upper
 */
public class RegisterController {  
    private Integer id;
    private MainApp mainApp;
    private FileInputStream imgLogo;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField imgPath;
    
    public void setMainApp(MainApp mainApp) throws Exception{
        this.mainApp = mainApp;
        this.id = mainApp.getIdUser();
    }

    @FXML
    private void handleVoltar() {
        mainApp.showLogin();
    }

    @FXML
    private void handleLimpar() {
        name.setText("");
        email.setText("");
        password.setText("");
    }

    @FXML
    public void handleCriar() throws Exception {
        UserDAO.getInstance().createPerson(name.getText(), email.getText(), password.getText(), imgLogo);
        mainApp.showLogin();
    }
    
    @FXML
    private void handleLoadLogo(ActionEvent event) throws Exception{
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Logo Empresa", "*.png","*.jpeg","*.jpg"));
        File f = fc.showOpenDialog(null);
        
        if(f != null){
            imgPath.setText(f.getAbsolutePath());       
            imgLogo = new FileInputStream(f.getAbsolutePath());
        }
    }

    @FXML
    private void handleDefaultLogo(ActionEvent event) throws Exception{
        //URL resource = getClass().getResource("defaultLogo.png");
        File f = new File("defaultLogo.png");
        imgPath.setText(f.getAbsolutePath());       
        imgLogo = new FileInputStream(f.getAbsolutePath());
    }
}
