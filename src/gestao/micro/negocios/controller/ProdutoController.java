/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao.micro.negocios.controller;

import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.ProductDAO;
import gestao.micro.negocios.model.Product;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author fael_
 */
public class ProdutoController {
    private Integer id;
    private MainApp mainApp;
    
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> tabName;
    @FXML
    private TableColumn<Product, Number> tabInv;
    @FXML
    private TableColumn<Product, String> tabType;
    @FXML
    private TableColumn<Product, Number> tabPrice;
    @FXML
    private TableColumn<Product, Number> tabUnit;

    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        tabName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tabInv.setCellValueFactory(cellData -> cellData.getValue().inventoryProperty());
        tabPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        tabUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        tabType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
 }    

    @FXML
    private void handleDelete() throws Exception {
        Product tempProduct = productTable.getSelectionModel().getSelectedItem();
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
           Alert del = new Alert (Alert.AlertType.CONFIRMATION);
           del.setTitle("Confirmar Exclusão");
           del.setHeaderText("Deseja realmente excluir o produto?");
           del.setContentText("Após a confirmação não será possível reverter a ação.");

           Optional<ButtonType> result = del.showAndWait();
           if (result.get() == ButtonType.OK){
            productTable.getItems().remove(selectedIndex);
            ProductDAO.getInstance(id).deleteProduct(tempProduct);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Produto Excluído");
            alert.setHeaderText(null);
            alert.setContentText("O produto " + tempProduct.getName()+ " foi excluído com sucesso!");

            alert.showAndWait();
           }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Produto Selecionado");
            alert.setContentText("Por favor, selecione um produto da tabela.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEdit() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
           mainApp.showProductEditDialog(selectedProduct, "edit");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Produto Selecionada");
            alert.setContentText("Por favor, selecione um produto na tabela.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCreate() throws Exception{
        Product tempProduct = new Product();
        boolean okClicked = mainApp.showProductEditDialog(tempProduct, "create");
        if (okClicked) {
            mainApp.getProductData().add(tempProduct);
        }
    }
    
     public void setMainApp(MainApp mainApp) throws Exception{
        this.mainApp = mainApp;
        this.id = mainApp.getIdUser();
    
        productTable.setItems(mainApp.getProductData());
    }    
     

    private void menuShowEstoque(ActionEvent event) {
        mainApp.showProduto();
    }

    private void menuShowFornecedores(ActionEvent event) {
        mainApp.showFornecedores();
    }

    
    private void menuLogout(ActionEvent event) {
        Alert del = new Alert (Alert.AlertType.CONFIRMATION);
        del.setTitle("Confirmar Saída");
        del.setHeaderText("Deseja realizar sair desta conta?");
        del.setContentText("Após a confirmação você será enviado para Login.");

        Optional<ButtonType> result = del.showAndWait();
        if (result.get() == ButtonType.OK){
            mainApp.showLogin();
        }
    }    
}
