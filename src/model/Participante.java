package model;
//import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Participante {
    
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleStringProperty apellidopaterno = new SimpleStringProperty();
    public SimpleStringProperty apellidomaterno = new SimpleStringProperty();
    public SimpleStringProperty matricula = new SimpleStringProperty();
    
    
    public String getNombre(){
        return nombre.get();
    }
    
    public String getApellidopaterno(){
        return apellidopaterno.get();
    }
    
    public String getApellidomaterno(){
        return apellidomaterno.get();
    }
    
    public String getMatricula(){
        return matricula.get();
    }
    
}