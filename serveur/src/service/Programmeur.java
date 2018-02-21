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
	
	public boolean ident (String login, String mdp) {
		for(Programmeur p : programmeurs) {
			if(p.getLogin() == login && p.getMdp() == mdp) {
				return true;
			}
		}
		return false;
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
}
