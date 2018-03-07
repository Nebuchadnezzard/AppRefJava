package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceProg implements Runnable {

	private Socket client;

	public ServiceProg(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {

		// faire l'ident
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			pw = new PrintWriter(this.client.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			int[] nums = new int[2];
			pw.println("Entrez votre pseudonyme et votre numéro de document : ");
			String pseudo = null;
			pseudo = br.readLine();
			
			pw.println("Entrez votre mot de passe : ");
			String mdp = null;
			mdp = br.readLine();
			
			
			
		} catch (IdentifiantInconnuException | PasLibreException e) {
			pw.println(e.getMessage());
			try {
				this.client.close();
			} 
			catch (IOException e1) {
				System.err.println(e1.getMessage());
			}
		} 
		catch (IOException e) {
			System.err.println("Erreur lecture socket.");
		}

		// proposer un choix (nouveau service, maj, changer adresse srv ftp)

	}

	public void start() {
		(new Thread(this)).start();
	}

}
