package service;

import java.net.Socket;

public class ServiceProg implements Runnable {
	
	private Socket client;
	
	public ServiceProg(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		
		//faire l'ident
		//proposer un choix (nouveau service, maj, changer adresse srv ftp)
		
	}

	public void start() {
		(new Thread(this)).start();		
	}
	
	

}
