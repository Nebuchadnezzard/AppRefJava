package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;


public class ServiceProg implements Runnable {

	private Socket client;
	private String pseudo;
	private String mdp;
	private Scanner clavier = new Scanner(System.in);
	private URLClassLoader urlcl;
	private String fileDir;
	private URL[] url;

	public ServiceProg(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			PrintWriter pw = new PrintWriter(this.client.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

			pw.println("Entrez votre pseudonyme : ");
			pseudo = br.readLine();

			pw.println("Entrez votre mot de passe : ");
			mdp = br.readLine();

			if (!service.Programmeur.estProgrammeur(pseudo, mdp)) {
				pw.println("Vous n'êtes pas programmeur, vous allez être déconnecté");
				this.client.close();
			}
			Programmeur p = Programmeur.getProgrammeur(pseudo, mdp);

			while (true) {
				pw.println("Bienvenue dans votre gestionnaire dynamique d'activité ##1 : Ajouter une activité nouvelle"
						+ "##2 : Mettre-à-jour un service ##3 : Changer votre d’adresse de serveur ftp ##4 : Supprimer un service"
						+ "##5 : Quitter le logiciel");
				int choix = Integer.parseInt(br.readLine());
				switch (choix) {
				case 1:
					fileDir = "file:///" + p.getUrl();
					url = new URL[] { new URL(fileDir) };
					urlcl = new URLClassLoader(url); // à faire
					pw.println("Quel service voulez vous ajouter ?");
					try {
						String classeName = br.readLine();
						Class<? extends Service> classeChargée = (Class<? extends Service>) urlcl.loadClass(classeName);
						System.out.println(classeChargée.getPackage().getName());
						if (!classeChargée.getPackage().getName().contains(p.getLogin())) {
						} else {
							service.ServiceRegistry.addService(classeChargée);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 2:

					break;
				case 3:
					pw.println("Entrez votre nouvelle adresse ftp :");
					String urlFTP = br.readLine();
					p.setUrl(urlFTP);
					break;
				case 4:
					if (ServiceRegistry.videService()) {
						pw.println("Aucuns service n'ont été ajouté.");
						String temp = br.readLine();
					} else {
						fileDir = "file:///" + p.getUrl();
						url = new URL[] { new URL(fileDir) };
						urlcl = new URLClassLoader(url); // à faire
						if (!ServiceRegistry.videService()) {
							pw.println("Supprimer un de vos services." + ServiceRegistry.toStringue());
							try {
								String classeName = br.readLine();
								String res = p.getLogin() + "." + classeName;
								Class<? extends Service> classeChargée = (Class<? extends Service>) urlcl.loadClass(res);
								service.ServiceRegistry.removeService(classeChargée);
							} catch (Exception e) {
								System.out.println(e);
							}
						}
						break;
					}
				case 5:
					// client.close();
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				this.client.close();
			} catch (IOException e1) {
				System.err.println(e1.getMessage());
			}
		}

		// proposer un choix (nouveau service, maj, changer adresse srv ftp)

	}

	public void start() {
		(new Thread(this)).start();
	}

}
