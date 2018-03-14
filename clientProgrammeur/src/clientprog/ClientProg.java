package clientprog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProg {

	private final static int PORT_PROG = 5000;
	private final static String HOST = "localhost";

	public static void main(String[] args) {

		Socket s = null;
		try {
			s = new Socket(HOST, PORT_PROG);
			BufferedReader sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter sout = new PrintWriter(s.getOutputStream(), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Connecté au serveur " + s.getInetAddress() + ":" + s.getPort());

			//demande du login
			System.out.println(sin.readLine());
			sout.println(clavier.readLine());
			//demande du mdp
			System.out.println(sin.readLine());
			sout.println(clavier.readLine());

			while(true){
				// menu et choix des options
				String line;
				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
				// saisie clavier/envoie au service de la réponse
				sout.println(clavier.readLine());
				// réception/affichage de la réponse
				System.out.println(sin.readLine());
				sout.println(clavier.readLine());
			}

		} catch (IOException e) {
			System.err.println("Fin de la connexion");
		}
		// Refermer dans tous les cas la socket
		try {
			if (s != null)
				s.close();
		} catch (IOException e2) {

		}
	}

}
