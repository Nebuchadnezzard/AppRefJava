package service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique	

	static {
		// VECTOR : thead safe --- arraylist : non thread safe
		servicesClasses = new Vector<Class<?>>();
	}
	private static List<Class<?>> servicesClasses;

	// ajoute une classe de service apr�s contr�le de la norme BLTi
	public static void addService(Class<? extends Service> service) {
		/*  VERIFIER NORME BRI : 
		 * impl�menter l'interface BRi.Service
			ne pas �tre abstract
			�tre publique
			avoir un constructeur public (Socket) sans exception
			avoir un attribut Socket private final
			avoir une m�thode public static String toStringue() sans exception
		 */
		
		if(Modifier.isAbstract(service.getModifiers()))
				throw new RuntimeException("La classe est abstraite.");
		if(!Modifier.isPublic(service.getModifiers()))
			throw new RuntimeException("La classe n'est pas public.");
		
		try {
			Constructor<?> constr = service.getConstructor(Socket.class);
			if(!Modifier.isPublic(constr.getModifiers()))
				throw new RuntimeException("Le constructeur n'est pas public.");
				
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		Field[] f = service.getDeclaredFields() ;//service.getFields();
		boolean ok = false;
		
		System.out.println("Les types de param : (" + f.length + ")");
		for(Field fi : f){
			System.out.println(fi.getType());
			if( fi.getType().equals(Socket.class)){
				if( Modifier.isFinal(fi.getModifiers()))
					ok = true;
			}
		}
		
		if( !ok)
			throw new RuntimeException("Pas d'attributs Socket");
		
		System.out.println("ajout�");
		servicesClasses.add(service);
	}
	
	// renvoie la classe de service (numService -1)	
	public static Class<?> getServiceClass(int numService) {
		return servicesClasses.get(numService-1);
	}
	
	// liste les activit�s pr�sentes
	public static String toStringue() {
		String result = "Activit�s pr�sentes : ##\n";

		for(Class<?> classe : servicesClasses){
			result += classe.getSimpleName()+"\n";
		}

		return result;
	}

}
