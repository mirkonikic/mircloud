package mircloud_drive;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerCLI extends Thread{
	Boolean isRunning = true;  //If EXIT command is inputed server stops < indicator
	String cmnd;  //Stores current stdin
	ServerSocket srvr = null;  //Stores server socket so that you can manipulate it
	
	//Storage for all client sockets, max is 256
	Client[] clients = new Client[256];
	int minFree = 0; //If stored == 256 not accepting anymore
	
	//STDIN
	BufferedReader cli = new BufferedReader(new InputStreamReader(System.in));
	
	public void updateMin() 
	{
		for(int i = 0; i<256; i++) 
		{
			if(clients[i] == null) 
			{
				minFree = i;
				break;
			}
		}
	}
	
	//Check if stored<255, store in Clients array, 
	public int newClient(Socket client) 
	{
		if(minFree >= 256) 
		{
			return 1;
		}
		else 
		{
			//Always CLIENTS[stored-1] and stored
			minFree++;
			clients[minFree-1] = new Client(client, minFree-1);
			
			ServerThread server = new ServerThread(this, clients[minFree-1]);
			server.start();
			
			return 0;
		}
	}
	
	//Based on second param eg. NAME and third eg. mirko, open printwriter and send msg
	public void messageClient() 
	{
		srprint("SOON");
	}
	
	//Based on second param eg. NAME and third eg. mirko, close conn and flush everything
	public void removeClient()
	{
		srprint("SOON");
	}
	
	public void srShutDown() 
	{
		isRunning = false;
		try {
			for(int i = 0; i<256; i++) 
			{
				if(clients[i] != null) 
				{
					clients[i].socket.close();
					//clients[i] = null;
				}
			}
			srvr.close();
		} catch (IOException e) {
			srerror("Couldn't close server");
		}
	}
	
	//Print all usernames on LIST command, depending on second parameter
	public void srlist(String[] param) 
	{
		if(param.length < 2) 
		{
			srerror("Usage: LIST <parameter:NAME|CON|INET|DIR|PWD|PRIVS|ALL>");
		}
		else 
		{
			for(int i=0; i<256; i++) 
			{
				System.out.println(clients[i]);
				if(clients[i] == null) 
				{
					continue;
				}
				else 
				{
					switch(param[1]) 
					{
						case "NAME":
							srprint(i + ": " + clients[i].name);
							break;
						case "CON":
							srprint(i + ": " + clients[i].name + " " + clients[i].isConsole);
							break;
						case "INET":
							srprint(i + ": " + clients[i].name + " " + clients[i].inetv4);
							break;
						case "DIR":
							srprint(i + ": " + clients[i].mainFileDir);
							break;
						case "PWD":
							srprint(i + ": " + clients[i].name + " " + clients[i].pwd);
							break;
						case "PRIVS":
							srprint(i + ": " + clients[i].name + " a: " + clients[i].isAdministrator + " p: " + clients[i].isPremium);
							break;
						case "ALL":
							srprint(i + ": " + clients[i]);
							break;
						default:
							srprint(i + ": " + clients[i].name);
							break;
					}
				}
			}
		}
	}
	
	//With this constructor we initialize ServerSocket
	public ServerCLI(ServerSocket server) 
	{
		srvr = server;
	}
	
	//Formated print function, shorter than sysout and prettier also
	public void srprint(String sen) 
	{
		System.out.println("SERVER@ >> "+sen);
	}
	
	//Formated error function, doesnt break anything and better than e.printstacktrace()
	public void srerror(String err) 
	{
		System.out.println("SERVER@ !! Error: "+err);
	}
	
	//Main method of threads
	public void run() 
	{
		while(isRunning == true) //While noone inputed EXIT
		{
			try {
				cmnd = cli.readLine(); //Try to read line
				String[] parsed = cmnd.split("\\s+"); //Parse it similar to bash 
				
				switch(parsed[0]) //Check the command part
				{
					case "EXIT":          
						srShutDown();
						break;
					case "PRINT":
						srprint(cmnd.substring(6));
						break;
					case "LIST":
						srlist(parsed);
						break;
					case "MSG":
						messageClient();
						break;
					case "KICK":
						removeClient();
						break;
					default:
						srerror("Unrecognized command \'"+cmnd+"\'");
						break;
				}
			} catch (IOException e) {
				srerror("Couldn't read stdin...");
			}
		}
	}
}
