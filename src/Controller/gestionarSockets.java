package Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import View.menuPrincipal;

public class gestionarSockets {
    public static gestionarComics gestCom;
    public static gestionarColecciones gestCol;

    public static void main(String[] args) {
        Socket socket;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            ServerSocket servidor = new ServerSocket(5000);
            Servidor s1 = new Servidor(servidor);
            s1.start();
            socket = new Socket("localhost", 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            gestCom = new gestionarComics(in, out);
            menuPrincipal frame = new menuPrincipal();
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class Servidor extends Thread {
    private ServerSocket servidor;

    public Servidor(ServerSocket servidor) {
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket cliente = servidor.accept();

                ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
                gestionarConsultas serv = new gestionarConsultas(in, out);
                serv.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
