package gestao.micro.negocios.controller;

import gestao.micro.negocios.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import gestao.micro.negocios.model.Product;
import gestao.micro.negocios.dao.ProductDAO;
import java.sql.SQLException;


/**
 *
 * @author upper
 */

public class ProductEditDialogController {
    private Integer id;
    private MainApp mainApp;


    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    private Stage dialogStage;
    
    private String action;
    
    private Product product;
    private boolean okClicked = false;
  
    @FXML
    private TextField UnitField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;


    /**
     * Define o palco deste dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(MainApp mainApp) throws Exception{
        this.mainApp = mainApp;
        this.id = mainApp.getIdUser();
    }

    /**
     * Define a pessoa a ser editada no dialog.
     * 
     * @param person
     */
    public void setProduct(Product product) {
        this.product = product;

        nameField.setText(product.getName());
        priceField.setText(product.getPrice().toString());
        categoryField.setText(product.getType());
        quantityField.setText(product.getInventory().toString());
        UnitField.setText(product.getUnitPrice().toString());
    }
    
    public void setAction (String action){
        this.action = action;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws Exception {
        if (isInputValid()) {
            product.setName(nameField.getText());
            product.setPrice(Float.parseFloat(priceField.getText().replace(",", ".")));
            product.setType(categoryField.getText());
            product.setInventory(Integer.parseInt(quantityField.getText()));
            product.setUnitPrice(Float.parseFloat(UnitField.getText().replace(",", ".")));
            
            switch(action){
                case "edit":
                    ProductDAO.getInstance(id).editProduct(product);
                    break;
                case "create":
                    ProductDAO.getInstance(id).createProduct(product);
                    break;
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Nome inválido!\n"; 
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Preço inválido!\n"; 
        }
        if (categoryField.getText() == null || categoryField.getText().length() == 0) {
            errorMessage += "Tipo inválido!\n"; 
        }
        
        if (UnitField.getText() == null || UnitField.getText().length() == 0 || UnitField.getText().matches(".*(([a-z]+)|([A-Z]+)|(((\\.)|(\\,)).*((\\.)|(\\,)))).*")) {
            errorMessage += "Preço Unitário inválido!\n"; 
        }
        
        if (priceField.getText() == null || priceField.getText().length() == 0 || priceField.getText().matches(".*(([a-z]+)|([A-Z]+)|(((\\.)|(\\,)).*((\\.)|(\\,)))).*")) {
            errorMessage += "Preço Venda inválido!\n"; 
        }

        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "Estoque inválido!\n"; 
        } else {
            try {
                Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Estoque inválido (deve ser um inteiro)!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            	      alert.setTitle("Campos Inválidos");
            	      alert.setHeaderText("Por favor, corrija os campos inválidos");
            	      alert.setContentText(errorMessage);
                alert.showAndWait();
                
            return false;
        }
    }
}