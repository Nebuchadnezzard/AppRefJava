package service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

/**
 * @author couderc & vitas
 *
 */
public class ServiceAma implements Runnable {

	private Socket client;

	public ServiceAma(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			while (true) {
				out.println(ServiceRegistry.toStringue() + "##Tapez le numéro de service désiré :");
				int choix = Integer.parseInt(in.readLine());

				try {
					Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);
					Service serviceLance = classe.getConstructor(Socket.class).newInstance(client);
					Thread threadService = new Thread(serviceLance);
					threadService.start();
					//attente de la fin du service lancé
					threadService.join();
					//pour ne pas désynchroniser le client et le serviceAma à la fin d'un service
					in.readLine();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| InterruptedException e1) {
					e1.printStackTrace();
				}
			}

		} catch (IOException e) {
			// Fin du service
		}

		try {
			client.close();
		} catch (IOException e2) {
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();
	}

}
