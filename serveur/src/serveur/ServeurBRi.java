package serveur;

import java.io.*;
import java.net.*;

public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	private Class<? extends service.Service> classeService;

	// Ajouter un .class en paramètre
	public ServeurBRi(int port, Class<? extends service.Service> classeService) {
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.classeService = classeService;
	}

	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un Service associé au param du constructeur
	// qui va la traiter.
	public void run() {
		try {
			while(true) {
				//lancer le service avec la socket en param
			}
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		}
	}

	// restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {
			this.listen_socket.close();
		} catch (IOException e1) {
			
		}
	}

	// lancement du serveur
	public void lancer() {
		(new Thread(this)).start();
	}
}
