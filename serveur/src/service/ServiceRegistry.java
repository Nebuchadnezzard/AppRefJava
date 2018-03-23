package service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * @author couderc
 * cette classe est un registre de services
 * partagée en concurrence par les clients et les "ajouteurs" de services,
 * un Vector pour cette gestion est pratique
 */
public class ServiceRegistry {
	

	static {
		// VECTOR : thead safe --- arraylist : non thread safe
		servicesClasses = new Vector<Class<? extends Service>>();
	}
	private static List<Class<? extends Service>> servicesClasses;

	/**
	 * ajoute une classe de service après contrôle de la norme BRi
	 * @param service
	 */
	public static void addService(Class<? extends Service> service) {
		/*
		 * VERIFIER NORME BRI : implémenter l'interface BRi.Service ne pas être
		 * abstract être publique avoir un constructeur public (Socket) sans
		 * exception avoir un attribut Socket private final avoir une méthode
		 * public static String toStringue() sans exception
		 */

		if (Modifier.isAbstract(service.getModifiers()))
			throw new RuntimeException("La classe est abstraite.");
		if (!Modifier.isPublic(service.getModifiers()))
			throw new RuntimeException("La classe n'est pas public.");

		try {
			Constructor<?> constr = service.getConstructor(Socket.class);
			if (!Modifier.isPublic(constr.getModifiers()))
				throw new RuntimeException("Le constructeur n'est pas public.");

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		Field[] f = service.getDeclaredFields();// service.getFields();
		boolean ok = false;

		System.out.println("Les types de param : (" + f.length + ")");
		for (Field fi : f) {
			System.out.println(fi.getType());
			if (fi.getType().equals(Socket.class)) {
				if (Modifier.isFinal(fi.getModifiers()))
					ok = true;
			}
		}

		if (!ok)
			throw new RuntimeException("Pas d'attributs Socket");

		System.out.println("ajouté");
		synchronized (ServiceRegistry.class) {
			servicesClasses.add(service);
		}
	}

	/**
	 * enlève un service chargé
	 * @param service
	 */
	public static void removeService(Class<? extends Service> service) {
		System.out.println("retiré");
		synchronized (ServiceRegistry.class) {
			servicesClasses.remove(service);
		}
	}

	/**
	 * teste le fait que la liste soit vide
	 * @return
	 */
	public static boolean videService() {
		synchronized (ServiceRegistry.class) {
			return servicesClasses.isEmpty();
		}
	}
 
	/**
	 * renvoie la classe de service (numService -1)
	 * @param numService
	 * @return
	 */
	public static Class<? extends Service> getServiceClass(int numService) {
		synchronized (ServiceRegistry.class) {
			return servicesClasses.get(numService - 1);
		}
	}

	// liste les activités présentes
	/**
	 * retourne le nom de tout les services chargés
	 * @return
	 */
	public static String toStringue() {
		String result = "Activités présentes : ##";
		int i = 0;
		synchronized (ServiceRegistry.class) {
			for (Class<?> classe : servicesClasses) {
				++i;
				result += i + ") " + classe.getSimpleName() + "##";
			}
		}

		return result;
	}

	/**
	 * retourne le nom de tout les services chargés d'un programmeur
	 * @param login
	 * @return
	 */
	public static String toStringueProgrammeur(String login) {
		String result = "Activités présentes : ##";
		int i = 0;
		synchronized (ServiceRegistry.class) {
			for (Class<?> classe : servicesClasses) {
				if (classe.getPackage().getName().equals(login)) {
					++i;
					result += i + ") " + classe.getSimpleName() + "##";
				}
			}
		}
		return result;

	}

	/**
	 * Retourne le nom d'un service à partir de son numero dans la liste
	 * @param numService
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static String getNameServiceClass(int numService) throws ClassNotFoundException {
		synchronized (ServiceRegistry.class) {
			if (servicesClasses.get(numService - 1) == null) {
				throw new ClassNotFoundException("indice incorrect");
			}
			return servicesClasses.get(numService - 1).getSimpleName();
		}
	}

	/**
	 * Remplace un service par un autre
	 * @param numService
	 * @param remplacante
	 */
	@SuppressWarnings("unchecked")
	public static void replaceService(int numService, Class<?> remplacante) {
		synchronized (ServiceRegistry.class) {
			servicesClasses.set(numService - 1, (Class<? extends Service>) remplacante);
		}
	}

}
