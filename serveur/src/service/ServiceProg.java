package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

public class ServiceProg implements Runnable {

	private Socket client;

	public ServiceProg(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {

		// faire l'ident
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			pw = new PrintWriter(this.client.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			int[] nums = new int[2];
			pw.println("Entrez votre pseudonyme : ");
			String pseudo = null;
			pseudo = br.readLine();

			pw.println("Entrez votre mot de passe : ");
			String mdp = null;
			mdp = br.readLine();

			if (!Programmeur.estProgrammeur(pseudo, mdp)) {
				pw.println("Identification échouée, vous allez être déconnecté");
				this.client.close();
			}

			// proposer un choix (nouveau service, maj, changer adresse srv ftp)
			pw.println("Bonjour " + pseudo + ", que souhaitez vous faire ?");
			pw.println("1 : Ajouter un nouveau service");
			pw.println("2 : Mettre à jour un service");
			pw.println("3 : changer l'adresse du serveur FTP");
			
			int choix;
			
			choix = Integer.parseInt(br.readLine());
			
			while(choix < 1 || choix > 3) {
				pw.println("Choix inexistant, entrez un autre choix");
				choix = Integer.parseInt(br.readLine());
			}
			
			switch(choix) {
			case 1 :
				pw.println("Donnez l'adresse du dossier contenant votre nouveau service");
				String service = "file:///" + br.readLine(); 
				URL[] urls = new URL[]{new URL(service)};
				URLClassLoader urlcl = new URLClassLoader(urls);
				pw.println("Donnez le nom de votre nouveau service");
				String nomClasse = br.readLine();
				Class<? extends Service> nouvelleClasse = (Class<? extends Service>) urlcl.loadClass(nomClasse);
				ServiceRegistry.addService(nouvelleClasse);
			case 2 :
				
			case 3 :
				
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				this.client.close();
			} 
			catch (IOException e1) {
				System.err.println(e1.getMessage());
			}
		}

	}

	public void start() {
		(new Thread(this)).start();
	}

}
