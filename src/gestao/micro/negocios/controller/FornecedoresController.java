/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao.micro.negocios.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.ProductDAO;
import gestao.micro.negocios.dao.ProviderDAO;
import gestao.micro.negocios.model.Provider;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


/**
 * FXML Controller class
 *
 * @author Escola
 */
public class FornecedoresController implements Initializable {
    private MainApp mainApp;
    private Integer id;

    @FXML
    private TableColumn<Provider, String> tabProvider;
    @FXML
    private TableColumn<Provider, String> tabDetail;
    //@FXML
    //private TableColumn<Order, Integer> tabOrder;
    @FXML
    private TableView<Provider> providerTable;
    @FXML
    private TableColumn<?, ?> tabOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabProvider.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tabDetail.setCellValueFactory(cellData -> cellData.getValue().detailProperty());
       // tabOrder.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
    }    
    
    public void setMainApp(MainApp mainApp) throws Exception{
        this.mainApp = mainApp;
        this.id = mainApp.getIdUser();
        providerTable.setItems(mainApp.getProviderData());
    } 

    @FXML
    private void handleProvider() {
        Provider tempProvider = new Provider();
        boolean okClicked = mainApp.showProviderDialog(tempProvider, "create");
        if (okClicked) {
            mainApp.getProviderData().add(tempProvider);
        }
    }

    @FXML
    private void handleOrder(ActionEvent event) {
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

    @FXML
    private void handleEditProvider(ActionEvent event) {
        Provider tempProvider = providerTable.getSelectionModel().getSelectedItem();
        if (tempProvider != null) {
           mainApp.showProviderDialog(tempProvider, "edit");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Fornecedor Selecionado");
            alert.setContentText("Por favor, selecione um fornecedor na tabela.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteProvider(ActionEvent event) throws Exception{
        Provider tempProvider = providerTable.getSelectionModel().getSelectedItem();
        int selectedIndex = providerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
           Alert del = new Alert (Alert.AlertType.CONFIRMATION);
           del.setTitle("Confirmar Exclusão");
           del.setHeaderText("Deseja realmente excluir o cadastro do fornecedor?");
           del.setContentText("Após a confirmação não será possível reverter a ação.");

           Optional<ButtonType> result = del.showAndWait();
           if (result.get() == ButtonType.OK){
            providerTable.getItems().remove(selectedIndex);
            ProviderDAO.getInstance(id).deleteProvider(tempProvider);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Fornecedor Excluído");
            alert.setHeaderText(null);
            alert.setContentText("O fornecedor " + tempProvider.getName()+ " foi excluído com sucesso!");

            alert.showAndWait();
           }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Fornecedor Selecionado");
            alert.setContentText("Por favor, selecione um fornecedor da tabela.");
            alert.showAndWait();
        }
    }
}
