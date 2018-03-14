package service;

import java.util.ArrayList;
import java.util.List;

public class Programmeur {
	private String login;
	private String mdp;
	private String url;
	
	static {
		programmeurs = new ArrayList<Programmeur>();
	}
	private static List<Programmeur> programmeurs;
	
	public Programmeur(String login, String mdp, String url) {
		this.login = login;
		this.mdp = mdp;
		this.setUrl(url);
		
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
	
	public static String toStringProg() {
		String res = "";
		for (int i = 0; i<programmeurs.size(); i++){
			 res+= programmeurs.get(i).getLogin();
			 res+= programmeurs.get(i).getMdp();
			 res+= "\n";
		}
		return res;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static Programmeur getProgrammeur(String pseudo, String mdp) {
		for(Programmeur p : Programmeur.programmeurs) {
			if(p.getLogin().contains(pseudo) && p.getMdp().contains(mdp)) {
				return p;
			}
		}
		return null;
	}
}
