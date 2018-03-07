package appli;

import serveur.ServeurBRi;
import service.Service;
import service.ServiceProg;
import service.ServiceAma;


public class BRiLaunch {
	private final static int PORT_AMA = 3000;
	private final static int PORT_PROG = 5000;
	
	public static void main(String[] args) throws Exception {
		
		
		new Thread(new ServeurBRi(PORT_AMA, (Class<? extends Service>) ServiceAma.class)).start();
		new Thread(new ServeurBRi(PORT_PROG, (Class<? extends Service>) ServiceProg.class)).start();
		
		System.out.println("Serveur lanc�");
		/*
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		
		// URLClassLoader sur ftp
		String fileDir = "file:///Z:/apache-ftpserver-1.1.0/res/home/texte/classes/"; 
		URL[] urls = new URL[]{new URL(fileDir)};
		URLClassLoader urlcl = new URLClassLoader(urls);
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit� BRi");
		System.out.println("Pour ajouter une activit�, celle-ci doit �tre pr�sente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'int�grer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activit�");
		
		new Thread(new ServeurBRi(PORT_SERVICE)).start();
		
		System.out.println("Quel service voulez vous charger ?");
		while (true){
			try {
				String classeName = clavier.next();
				// charger la classe 
				Class<? extends Service> classchargee = (Class<? extends Service>) urlcl.loadClass(classeName);
				// d�clarer au ServiceRegistry
				ServiceRegistry.addService(classchargee);
				
				System.out.println(ServiceRegistry.toStringue());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		*/		
	}
}
