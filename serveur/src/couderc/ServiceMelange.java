package couderc;

import java.io.*;
import java.net.*;

import service.Service;

// rien à ajouter ici
public class ServiceMelange implements Service {

	private final Socket client;

	public ServiceMelange(Socket socket) {
		client = socket;
	}
	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
		PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

		out.println("Tapez un texte à mélanger");

		String line = in.readLine();		

		char[] characters = line.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			int randomIndex = (int)(Math.random() * characters.length);
			char temp = characters[i];
			characters[i] = characters[randomIndex];
			characters[randomIndex] = temp;
		}
		out.println(characters);


		}
		catch (IOException e) {
			//Fin du service 
		}
	}

}
