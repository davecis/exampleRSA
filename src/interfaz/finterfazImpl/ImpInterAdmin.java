package interfaz.finterfazimpl;
/*Clase que implementa los metodos de la interfaz remota y extiende a la interfaz de usuario Application de Java FX*/

import interfaz.InterfazAdmin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/*JavaFX es una API que extiende de la clase Application. La funcion lauch() manda a llamar el metodo start para hacer la interfaz*/
public class ImpInterAdmin extends Application implements InterfazAdmin {

	@Override
	public void start(Stage stage) throws Exception {
		//crea una instancia del archivo XML que contiene la interfaz grafica
		Parent root = FXMLLoader.load(getClass().getResource("administrador.fxml"));
        
        //crea un escenario con el archivo fxml
        Scene scene = new Scene(root);
        
        //muestra y agrega el escenario a la escena
        stage.setScene(scene);
        stage.show();
    }

    /*Metodos que implementan la interfaz remota*/
    public void doApp() {
    	//manda a llamar al metodo start()
        launch();
    }

    public String sayHello() {
        return "Hello, world!";
    }
}