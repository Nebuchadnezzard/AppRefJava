package vitas;

import java.io.*;
import java.math.BigInteger;
import java.net.*;

import service.Service;

public class ServiceAscii implements Service {
	
	private final Socket client;
	
	public ServiceAscii(Socket socket) {
		client = socket;
	}
@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Tapez un texte");
		
			String line = in.readLine();		
			
		    StringBuilder sb = new StringBuilder();
		    for (char c : line.toCharArray())
		    sb.append((int)c);
		    BigInteger mInt = new BigInteger(sb.toString());
			out.println(mInt);
			
		}
		catch (IOException e) {
			//Fin du service
		}
	}
	
}
