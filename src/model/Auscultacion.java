package model;
import javafx.beans.property.SimpleStringProperty;


public class Auscultacion {
    
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleStringProperty descripcion = new SimpleStringProperty();
    public SimpleStringProperty fechainicio = new SimpleStringProperty();
    public SimpleStringProperty fechafin = new SimpleStringProperty();
    
    
    public String getNombre(){
        return nombre.get();
    }
    
    public String getDescripcion(){
        return descripcion.get();
    }
    
    public String getFechafin(){
        return fechafin.get();
    }
    
    public String getFechainicio(){
        return fechainicio.get();
    }
    
}