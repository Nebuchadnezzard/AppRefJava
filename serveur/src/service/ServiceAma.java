package service;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;


public class ServiceAma implements Runnable {
	
	private Socket client;
	
	public ServiceAma(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+"##Tapez le nom de service désiré :");
			int choix = Integer.parseInt(in.readLine());
			
			String serv = ServiceRegistry.getServiceClass(choix).getName();
			try {
				Class classe = Class.forName(serv);
				Class[] types = new Class[]{Socket.class};
				Constructor co = classe.getConstructor(types);
				Object o = co.newInstance(new Socket[]{client});
				Method m = classe.getMethod("run", null);
				m.invoke(o, null);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// instancier le service de string "choix" en lui passant la socket "client"
			// invoquer run() pour cette instance ou la lancer dans un thread à part 
				
			}
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
