package model;
//import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Opcion {
    
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleStringProperty descripcion = new SimpleStringProperty();
    
    
    public String getNombre(){
        return nombre.get();
    }
    
    public String getDescripcion(){
        return descripcion.get();
    }
    
}