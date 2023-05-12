package gestao.micro.negocios.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import gestao.micro.negocios.MainApp;

/**
 *
 * @author upper
 */
public class DashboardController {

    private MainApp mainApp;
    
    @FXML
    private void initialize() {
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
