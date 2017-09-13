package interfaz;
/*Interfaz remota que define los metodos a compartir por el servidor*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazAdmin extends Remote {
    String sayHello() throws RemoteException;

    void doApp() throws RemoteException;
}