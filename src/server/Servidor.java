package server;
/*Servidor RMI que hereda los metodos de la clase que implementa la interfaz remota*/

import interfaz.InterfazAdmin;
import interfaz.finterfazimpl.ImpInterAdmin;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends ImpInterAdmin {
        
    public Servidor() {}
        
    public static void main(String args[]) {
        
        
        try {
        	//Crea el objeto remoto tipo Servidor
            Servidor obj = new Servidor();

            /*Obtiene el objeto proxy
            	UnicastRemoteObject: El objeto se ejecutara en la maquina virtual remota
            	exportObject: liga el objeto remoto a un determinado puerto, el cero es el puerto determinado(1099)
            */
            InterfazAdmin stub = (InterfazAdmin) UnicastRemoteObject.exportObject(obj, 0);

            /* Obiene el registro y enlaza el objeto remoto stub al registro RMI
            	bind: obtiene 2 parametros la interfaz a registrar y el objeto proxy 
            */
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("InterfazAdmin", stub);

            System.err.println("Seridor listo");
        } catch (Exception e) {
        	//Maneja las Excepciones provenientes del servidor
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

/*Para iniciar el servicio de java rmi registry se escribe en la terminal: Wnodws: start rmiregistry Linux: rmiregistry & (solo donde este el servidor), 
si se requiere cambiar de puerto se hace de la siguiente manera start rmiregistry 2001.
El servicio se inicia en el puerto 1099 por defecto.*/