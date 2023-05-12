package gestao.micro.negocios.controller;

import gestao.micro.negocios.MainApp;
import javafx.fxml.FXML;

/**
 *
 * @author upper
 */
public class RootController {
    

    private MainApp mainApp;

    @FXML
    private void initialize() {
    }
    
    @FXML
    private void handleProduct() {
        mainApp.showProduto();
    }
    
    @FXML
    private void handleDashboard() {
        mainApp.showDashboard();
    }
    
    @FXML
    private void handleSair() {
        mainApp.showLogin();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
