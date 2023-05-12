package gestao.micro.negocios;


import gestao.micro.negocios.dao.*;
import gestao.micro.negocios.model.*;
import gestao.micro.negocios.controller.*;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane mainPane;
    private AnchorPane viewPane;
    private Label screenName;
    private Integer idUser;
    private static MainApp instance;

    public static MainApp getInstance() throws Exception {
        if (instance == null) {
            instance = new MainApp();
        }
        return instance;
    }
    
    public void MainApp(Integer id){
        this.idUser = id;
    }
    
    public ObservableList<Product> getProductData() {
        ObservableList<Product> productData = FXCollections.observableArrayList();
        try {
            List<Product> prdList = ProductDAO.getInstance(idUser).getProductList();
            for (int i = 0; i < prdList.size(); i++) {
                productData.add(new Product(Integer.toString(prdList.get(i).getId()), Integer.toString(prdList.get(i).getInventory()),
                        prdList.get(i).getName(),prdList.get(i).getType(),prdList.get(i).getUnitPrice().toString(),
                        prdList.get(i).getPrice().toString()));
            }
        }catch(Exception e){}
        
        return productData;
    }
    
    public ObservableList<Provider> getProviderData() {
        ObservableList<Provider> providerData = FXCollections.observableArrayList();
        try {
            List<Provider> prvList = ProviderDAO.getInstance(idUser).getProviderList();
            for (int i = 0; i < prvList.size(); i++) {
                providerData.add(new Provider(prvList.get(i).getName(), prvList.get(i).getDetail(), prvList.get(i).getId().toString()));
            }
        }catch(Exception e){}
        
        return providerData;
    }
    
    public ObservableList<Customer> getCustomerData() {
        ObservableList<Customer> customerData = FXCollections.observableArrayList();
        try {
            List<Customer> custList = CustomerDAO.getInstance(idUser).getCustomerList();
            for (int i = 0; i < custList.size(); i++) {
                customerData.add(new Customer(custList.get(i).getId().toString(), custList.get(i).getName().toString(),
                        custList.get(i).getEmail().toString(), custList.get(i).getTelefone().toString()));
            }
        }catch(Exception e){}
        
        return customerData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestão Micro Negócio");
        
        primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("appIcon.png")));

        
        showLogin();
    }
    
    /**
     * Inicializa o root layout (layout base).
     */
     public void initMain() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Main.fxml"));
            mainPane = (AnchorPane) loader.load();
            
            AnchorPane a = (AnchorPane) mainPane.getChildren().get(0);
            Pane abc = (Pane) a.getChildren().get(0);
            screenName = (Label) abc.getChildren().get(0);
            
            screenName.setText("Menu");

            viewPane = (AnchorPane) mainPane.getChildren().get(1);
                        
            Scene scene = new Scene(mainPane);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            MainController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setIdUser(Integer id){
        this.idUser = id;
    }
    
    public Integer getIdUser(){
        return this.idUser;
    }
     
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            RootController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            Scene scene = new Scene(login);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            LoginController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegister() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Register.fxml"));
            AnchorPane register = (AnchorPane) loader.load();

             Scene scene = new Scene(register);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            RegisterController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Dashboard.fxml"));
            AnchorPane dash = (AnchorPane) loader.load();

            viewPane.getChildren().setAll(dash);

            DashboardController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showProduto() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Produto.fxml"));
            AnchorPane productOverview = (AnchorPane) loader.load();

            viewPane.getChildren().setAll(productOverview);
            screenName.setText("Estoque");


            ProdutoController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean showProductEditDialog(Product product,String action) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(action.equals("edit") ? "Editar Produto" : "Cadastrar Produto");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProductEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(product);
            controller.setAction(action);
            controller.setMainApp(this);


            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showProviderDialog(Provider provider,String action) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProviderDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(action.equals("edit") ? "Editar Fornecedor" : "Cadastrar Fornecedor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProviderDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProvider(provider);
            controller.setAction(action);
            controller.setMainApp(this);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void showFornecedores() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Fornecedores.fxml"));
            AnchorPane fornecedores = (AnchorPane) loader.load();

            viewPane.getChildren().setAll(fornecedores);
            screenName.setText("Fornecedores");


            FornecedoresController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public boolean showCustomerDialog(Customer customer,String action) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CustomerDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(action.equals("edit") ? "Editar Cliente" : "Cadastrar Cliente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CustomerDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCustomer(customer);
            controller.setAction(action);
            controller.setMainApp(this);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void showClientes() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Customer.fxml"));
            AnchorPane customer = (AnchorPane) loader.load();

            viewPane.getChildren().setAll(customer);
            screenName.setText("Cliente");


            CustomerController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
            return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
