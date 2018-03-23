package service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author couderc & vitas
 *
 */
public class Programmeur {
	private String login;
	private String mdp;
	private String url;

	static {
		//liste de programmeurs statique
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

	/**
	 * teste si un programmeur associé à un login et mdp existe
	 * @param login
	 * @param mdp
	 * @return
	 */
	public static boolean estProgrammeur(String login, String mdp) {
		synchronized (Programmeur.class) {
			for (Programmeur p : Programmeur.programmeurs) {
				if (p.getLogin().equals(login) && p.getMdp().equals(mdp)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * retourne en string tout les programmeurs (test)
	 * @return
	 */
	public static String toStringProg() {
		String res = "";
		synchronized (Programmeur.class) {
			for (int i = 0; i < programmeurs.size(); i++) {
				res += programmeurs.get(i).getLogin();
				res += programmeurs.get(i).getMdp();
				res += "\n";
			}
		}
		return res;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * retourne un programmeur à partie d'un login et mdp
	 * @param pseudo
	 * @param mdp
	 * @return
	 */
	public static Programmeur getProgrammeur(String pseudo, String mdp) {
		synchronized (Programmeur.class) {
			for (Programmeur p : Programmeur.programmeurs) {
				if (p.getLogin().equals(pseudo) && p.getMdp().equals(mdp)) {
					return p;
				}
			}
		}
		return null;
	}
}
