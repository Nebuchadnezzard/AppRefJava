package vitas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import service.Service;


//rien à ajouter ici
public class ServiceInversion implements Service {
	
	private final Socket client;
	
	public ServiceInversion(Socket socket) {
		client = socket;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			
			out.println("Tapez un texte à inverser");
		
			String line = in.readLine();		
	
			String invLine = new String (new StringBuffer(line).reverse());
			
			out.println(invLine);
			
		}
		catch (IOException e) {
			//Fin du service d'inversion
		}
	}

}