package gestao.micro.negocios.controller;

import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.CustomerDAO;
import gestao.micro.negocios.dao.ProviderDAO;
import gestao.micro.negocios.model.Customer;
import gestao.micro.negocios.model.Provider;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author fael_
 */
public class CustomerController implements Initializable {

    private MainApp mainApp;
    private Integer id;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> tabName;
    @FXML
    private TableColumn<Customer, String> tabEmail;
    @FXML
    private TableColumn<Customer, String> tabTelephone;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnNew;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tabEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        tabTelephone.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
    }
    
    public void setMainApp(MainApp mainApp) throws Exception{
        this.mainApp = mainApp;
        this.id = mainApp.getIdUser();
        customerTable.setItems(mainApp.getCustomerData());
    }
    
    @FXML
    private void handleCreate(ActionEvent event) {
        Customer tempCustomer = new Customer();
        boolean okClicked = mainApp.showCustomerDialog(tempCustomer, "create");
        if (okClicked) {
            mainApp.getCustomerData().add(tempCustomer);
        }
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        Customer tempCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (tempCustomer != null) {
           mainApp.showCustomerDialog(tempCustomer, "edit");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Cliente Selecionado");
            alert.setContentText("Por favor, selecione um cliente na tabela abaixo.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) throws Exception {
        Customer tempCustomer = customerTable.getSelectionModel().getSelectedItem();
        int selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
           Alert del = new Alert (Alert.AlertType.CONFIRMATION);
           del.setTitle("Confirmar Exclusão");
           del.setHeaderText("Deseja realmente excluir esse cliente?");
           del.setContentText("Após a confirmação não será possível reverter a ação.");

           Optional<ButtonType> result = del.showAndWait();
           if (result.get() == ButtonType.OK){
            customerTable.getItems().remove(selectedIndex);
            CustomerDAO.getInstance(id).deleteCustomer(tempCustomer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cliente Excluído");
            alert.setHeaderText(null);
            alert.setContentText("O cliente " + tempCustomer.getName()+ " foi excluído com sucesso!");

            alert.showAndWait();
           }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Cliente Selecionado");
            alert.setContentText("Por favor, selecione um cliente da tabela abaixo.");
            alert.showAndWait();
        }
    }
}
