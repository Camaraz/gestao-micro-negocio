package gestao.micro.negocios.model;

import javafx.beans.property.*;

/**
 *
 * @author upper
 */
public class Provider {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty detail;

    public Provider() {
        this(null, null, "0");
    }

    public Provider(String name, String detail, String id) {
        this.name = new SimpleStringProperty(name);
        this.detail = new SimpleStringProperty(detail);
        this.id = new SimpleIntegerProperty(Integer.parseInt(id));
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

    public String getDetail() {
        return detail.get();
    }

    public void setDetail(String email) {
        this.detail.set(email);
    }

    public StringProperty detailProperty() {
        return detail;
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
}
