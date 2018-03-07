package serveur;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	private Constructor<?> constructeurService;

	// Ajouter un .class en paramètre
	public ServeurBRi(int port, Class<? extends service.Service> classeService) throws NoSuchMethodException, SecurityException {
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.constructeurService = classeService.getDeclaredConstructor(java.net.Socket.class);
	}

	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un Service associé au param du constructeur
	// qui va la traiter.
	public void run() {
		try {
			while(true) {
				//lancer le service avec la socket en param
				Socket socketClient = listen_socket.accept();
				new Thread((Runnable) this.constructeurService.newInstance(socketClient)).start();
			}
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :"+e);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
