package appli;

import serveur.ServeurBRi;
import service.Programmeur;
import service.Service;
import service.ServiceProg;
import service.ServiceAma;


public class BRiLaunch {
	private final static int PORT_AMA = 3000;
	private final static int PORT_PROG = 5000;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		//lance les deux serveurs sur des ports différents
		new Thread(new ServeurBRi(PORT_AMA, (Class<? extends Service>) ServiceAma.class)).start();
		new Thread(new ServeurBRi(PORT_PROG, (Class<? extends Service>) ServiceProg.class)).start();
		
		// Création en dur de programmeurs
		new Programmeur("vitas", "vitas", "localhost:2121/");
		new Programmeur("couderc", "couderc", "localhost:2121/");
		
		System.out.println("Serveur lancé");
		
	}
}
