package gestao.micro.negocios.controller;

import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.CustomerDAO;
import gestao.micro.negocios.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fael_
 */
public class CustomerDialogController implements Initializable {

    private MainApp mainApp;
    private Integer id;
    private Stage dialogStage;    
    private String action;
    private Customer customer;    
    private boolean okClicked = false;
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

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
            customer.setName(nameField.getText());
            customer.setEmail(emailField.getText());
            customer.setTelefone(telephoneField.getText());
            
            switch(action){
                case "edit":
                    CustomerDAO.getInstance(id).editCustomer(customer);
                    break;
                case "create":
                    CustomerDAO.getInstance(id).createCustomer(customer.getName(), customer.getEmail(), customer.getTelefone());
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
    
    public void setCustomer(Customer cust) {
        this.customer = cust;

        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        telephoneField.setText(customer.getTelefone());
    }
    
    public boolean isOkClicked() { 
        return okClicked;
    }
    
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Nome inválido!\n"; 
        }
        
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "Email inválido!\n";
        }
        
        if (telephoneField.getText() == null || telephoneField.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
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
