/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao.micro.negocios.controller;

import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.ProviderDAO;
import gestao.micro.negocios.model.Provider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Escola
 */
public class ProviderDialogController implements Initializable {
    private MainApp mainApp;
    private Integer id;

    @FXML
    private TextField nomeField;
    @FXML
    private TextField detalheField;
    
    private Stage dialogStage;
    
    private String action;

    private Provider provider;
    
    private boolean okClicked = false;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setMainApp(MainApp mainApp) throws Exception{
        this.mainApp = mainApp;
        this.id = mainApp.getIdUser();
    }

    @FXML
    private void handleOk(ActionEvent event) throws Exception{
        if (isInputValid()) {
            provider.setName(nomeField.getText());
            provider.setDetail(detalheField.getText());
            
            switch(action){
                case "edit":
                    ProviderDAO.getInstance(id).editProvider(provider);
                    break;
                case "create":
                    ProviderDAO.getInstance(id).createProvider(provider.getName(), provider.getDetail());
                    break;
            }
            okClicked = true;
            dialogStage.close();
        }
        
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }
    
    public void setAction (String action){
        this.action = action;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setProvider(Provider provider) {
        this.provider = provider;

        nomeField.setText(provider.getName());
        detalheField.setText(provider.getDetail());
    }
    
    public boolean isOkClicked() { 
        return okClicked;
    }
    
    private boolean isInputValid() {
        String errorMessage = "";

        if (nomeField.getText() == null || nomeField.getText().length() == 0) {
            errorMessage += "Nome inválido!\n"; 
        }
        
        if (detalheField.getText() == null || detalheField.getText().length() == 0) {
            errorMessage += "Detalhe inválido!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
            	      alert.setTitle("Campos Inválidos");
            	      alert.setHeaderText("Por favor, corrija os campos inválidos");
            	      alert.setContentText(errorMessage);
                alert.showAndWait();
                
            return false;
        }
    }
}
