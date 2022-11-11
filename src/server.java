import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Controller.*;

public class server extends Thread {
    private ServerSocket servidor;

    public server(ServerSocket servidor) {
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            System.out.println("SERVIDOR INICIADO");
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("CLIENTE ACEPTADO");

                ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
                gestionarConsultas serv = new gestionarConsultas(in, out);
                serv.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            ServerSocket servidor = new ServerSocket(5000);
            server s1 = new server(servidor);
            s1.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}