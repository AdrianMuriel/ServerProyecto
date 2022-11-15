package View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Controller.gestionarConsultas;

public class server extends Thread {
	private ServerSocket servidor;
	private Socket cliente;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private gestionarConsultas serv;

	public server(ServerSocket servidor) {
		this.servidor = servidor;
	}

	@Override
	public void run() {
		try {
			System.out.println("SERVIDOR INICIADO");
			while (true) {
				cliente = servidor.accept();
				System.out.println("CLIENTE ACEPTADO");

				in = new ObjectInputStream(cliente.getInputStream());
				out = new ObjectOutputStream(cliente.getOutputStream());
				serv = new gestionarConsultas(in, out);
				serv.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		try {
			if (in != null) {
				in.close();
				out.close();
			}
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}