package service;

import java.util.ArrayList;
import java.util.List;

public class Programmeur {
	private String login;
	private String mdp;
	
	static {
		programmeurs = new ArrayList<Programmeur>();
	}
	private static List<Programmeur> programmeurs;
	
	public Programmeur(String login, String mdp) {
		this.login = login;
		this.mdp = mdp;
		
		programmeurs.add(this);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public static boolean estProgrammeur(String login, String mdp) {
		for(Programmeur p : Programmeur.programmeurs) {
			if(p.getLogin().contains(login) && p.getMdp().contains(mdp)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getProgrammeur() {
		String res = "";
		for (int i = 0; i<programmeurs.size(); i++){
			 res+= programmeurs.get(i).getLogin();
			 res+= programmeurs.get(i).getMdp();
			 res+= "\n";
		}
		return res;
	}
}
