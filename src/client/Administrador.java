package client;
/*Cliente RMI*/
import interfaz.InterfazAdmin;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Administrador {

    private Administrador() {}

    public static void main(String[] args) {
    	//Operador ternario que obtiene la IP del servidor por argumento, si no se pasa, es null
        String host = (args.length < 1) ? null : args[0];

        try {
        	//Obtiene el registro del servidor con el parametro de la IP del servidor, si es null sera local
            Registry registry = LocateRegistry.getRegistry(host);

            //Obtiene la interfaz remote registrada en el registro de RMI del servidor
            InterfazAdmin stub = (InterfazAdmin) registry.lookup("InterfazAdmin");

            //Accede a los metodos implementados por la interfaz remota
            String response = stub.sayHello();
            stub.doApp();

            System.out.println("response: " + response);
        } catch (Exception e) {
        	//Maneja las Excepciones provenientes del cliente
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}