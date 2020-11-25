package mircloud_drive;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerMain {
	public static void main(String[] args) {
		int port = 9090; //Stores port number
		Socket klijent = null; //Stores clients socket object
		
		try {
			ServerSocket server = new ServerSocket(port); //Opens server socket
			
			ServerCLI server_cli = new ServerCLI(server); //Starts server terminal
			server_cli.start(); //START
			
			//While server is on
			while(server_cli.isRunning == true) 
			{
				klijent = server.accept(); //New connection!
				if (server_cli.newClient(klijent) == 0) 
				{
					server_cli.srprint("New connection appeared : "+klijent.getRemoteSocketAddress());
				}
				else
				{
					server_cli.srerror("No space left for connections!");
				} //Check if you can add new user and store him into array, if no space print rip
			}
			
			server.close();
			
		} catch (IOException e) {
			System.out.println("SERVER@ >> Successfully closed");
		}
	}
}
