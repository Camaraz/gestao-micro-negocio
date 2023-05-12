package gestao.micro.negocios.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fael_
 */
public class Customer {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty telefone;

    public Customer(String id, String name, String email, String telephone) {
        this.id = new SimpleIntegerProperty(Integer.parseInt(id));
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.telefone = new SimpleStringProperty(telephone);
    }
    
    public Customer(){
        this("0",null,null,null);
    }

    
    public Integer getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(Integer.parseInt(id));
    }
    public IntegerProperty idProperty() {
        return id;
    } 

    
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return name;
    }

    
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public StringProperty emailProperty() {
        return email;
    }

    
    public String getTelefone() {
        return telefone.get();
    }
    public void setTelefone(String telephone) {
        this.telefone.set(telephone);
    }
    public StringProperty telephoneProperty() {
        return telefone;
    }
}
