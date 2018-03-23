package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * @author couderc & vitas
 *
 */
public class ServiceProg implements Runnable {

	private Socket client;
	private String pseudo;
	private String mdp;
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

			//identification programmeur
			pw.println("Entrez votre pseudonyme : ");
			pseudo = br.readLine();

			pw.println("Entrez votre mot de passe : ");
			mdp = br.readLine();

			// teste si le login et mdp sont valide
			if (!service.Programmeur.estProgrammeur(pseudo, mdp)) {
				pw.println("Vous n'�tes pas programmeur, vous allez �tre d�connect�");
				this.client.close();
			}
			Programmeur p = Programmeur.getProgrammeur(pseudo, mdp);

			while (true) {
				//menu
				pw.println("Bienvenue dans votre gestionnaire dynamique d'activit� ##1 : Ajouter une activit� nouvelle"
						+ "##2 : Mettre-�-jour un service ##3 : Changer votre d�adresse de serveur ftp ##4 : Supprimer un service"
						+ "##ou appuyez sur entr�e pour quitter");
				
				int choix = Integer.parseInt(br.readLine());
				
				switch (choix) {
				case 1:
					//ajouter service
					fileDir = "ftp://" + p.getUrl();
					urlcl = new URLClassLoader(new URL[] {new URL(fileDir)}); 
					pw.println("Quel service voulez vous ajouter ?");
					try {
						String classeName = br.readLine();
						@SuppressWarnings("unchecked")
						Class<? extends Service> classeCharg�e = (Class<? extends Service>) urlcl.loadClass(p.getLogin() + "." + classeName);
						service.ServiceRegistry.addService(classeCharg�e);
					} 
					catch (ClassNotFoundException e) {
						pw.println("La classe est introuvable, appuyer sur entr�e pour continuer");
						br.readLine();
					}
					break;
				case 2:
					//maj service
					pw.println("Entrez le num�ro de la classe � charger parmi cette liste : " + ServiceRegistry.toStringueProgrammeur(p.getLogin()));
					int numService = Integer.parseInt(br.readLine());

					//r�cup�ration de la nouvelle version du service
					pw.println("Chemin de la nouvelle version du service sur le serveur ftp : ");
					String urlUpdateService = "ftp://" + p.getUrl() + br.readLine();
					URLClassLoader urlcl = new URLClassLoader (new URL[] {new URL(urlUpdateService) });
					Class<?> remplacante = null;
					//cherche la classe avec un nom identique � la remplac�e
					try {
						remplacante = urlcl.loadClass(ServiceRegistry.getNameServiceClass(numService));
					}
					catch(ClassNotFoundException e) {
						pw.println("La classe que vous cherchez n'est pas sur le serveur ftp");
						break;
					}
					//remplacement
					ServiceRegistry.replaceService(numService, remplacante);
					break;
				case 3:
					//nouvelle addresse ftp pour le programmeur
					pw.println("Entrez votre nouvelle adresse ftp :");
					String urlFTP = br.readLine();
					p.setUrl(urlFTP);
					break;
				case 4:
					//d�sactive un service du programmeur connect� 
					if (ServiceRegistry.videService()) {
						pw.println("Aucun service n'ont �t� ajout�.");
						@SuppressWarnings("unused")
						String useless = br.readLine();
					} else {
						fileDir = "ftp://" + p.getUrl();
						url = new URL[] { new URL(fileDir) };
						urlcl = new URLClassLoader(url); // � faire
						if (!ServiceRegistry.videService()) {
							pw.println("Supprimer un de vos services en entrant son nom :" + ServiceRegistry.toStringueProgrammeur(p.getLogin()));
							try {
								String classeName = br.readLine();
								String res = p.getLogin() + "." + classeName;
								@SuppressWarnings("unchecked")
								Class<? extends Service> classeCharg�e = (Class<? extends Service>) urlcl.loadClass(res);
								service.ServiceRegistry.removeService(classeCharg�e);
							} 
							catch (Exception e) {
								System.out.println(e);
							}
						}
					}
					break;
				default:
					pw.println("Nombre invalide.");
					@SuppressWarnings("unused") String useless = br.readLine();
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


	}

	/**
	 * 
	 */
	public void start() {
		(new Thread(this)).start();
	}

}
