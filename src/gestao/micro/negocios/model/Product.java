package gestao.micro.negocios.model;

import javafx.beans.property.*;


/**
 *
 * @author upper
 */
public class Product {
    private final IntegerProperty id;
    private final StringProperty name;
    private final FloatProperty price;
    private final FloatProperty unitprc;
    private final IntegerProperty inventory;
    private final StringProperty type;


    public Product() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.price = new SimpleFloatProperty(0);
        this.unitprc = new SimpleFloatProperty(0);
        this.inventory = new SimpleIntegerProperty(0);
        this.type = new SimpleStringProperty("");
    }
 
    public Product(String id, String qtd, String desc, String cat, String unit, String value) {
        this.id = new SimpleIntegerProperty(Integer.parseInt(id));
        this.name = new SimpleStringProperty(desc);
        this.price = new SimpleFloatProperty(Float.parseFloat(value));
        this.unitprc = new SimpleFloatProperty(Float.parseFloat(unit));
        this.inventory = new SimpleIntegerProperty(Integer.parseInt(qtd));
        this.type = new SimpleStringProperty(cat);
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

    public Float getPrice() {
        return price.get();
    }

    public void setPrice(Float price) {
        this.price.set(price);
    }

    public Float getUnitPrice() {
        return unitprc.get();
    }

    public void setUnitPrice(Float price) {
        this.unitprc.set(price);
    }
    
    public FloatProperty unitProperty() {
        return unitprc;
    }
    
    public FloatProperty priceProperty() {
        return price;
    }

    public Integer getInventory() {
        return inventory.get();
    }
    
    public Integer getId() {
        return id.get();
    }

    public void setInventory(Integer inventory) {
        this.inventory.set(inventory);
    }

    public IntegerProperty inventoryProperty() {
        return inventory;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }
}
