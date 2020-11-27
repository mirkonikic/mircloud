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
			clients[minFree-1].cliThread = server; //STORUJEM ovaj thread u klijentu
			server.start();
			
			return 0;
		}
	}
	
	//TRAZI SPECIFIC KLIJENTA U CLIENT STORAGE AND RETURNUJE AKO GA NADJE
	public Client findClient(String sen, int arg) 
	{
		if(arg == 0) //ID 
		{
			for(int i=0; i<256; i++) 
			{
				//System.out.println(clients[i]);
				if(clients[i] == null) 
				{
					continue;
				}
				else 
				{
					if(clients[i].socketID == Integer.parseInt(sen)) 
					{
						return clients[i];
					}
				}
			}
		}
		else if(arg == 1) //NAME 
		{
			for(int i=0; i<256; i++) 
			{
				//System.out.println(clients[i]);
				if(clients[i] == null) 
				{
					continue;
				}
				else 
				{
					if(clients[i].name.equals(sen)) 
					{
						return clients[i];
					}
				}
			}
		}

			
		//AKO NE NADJE RETURN NULL;
		return null;
	}
	
	//Based on second param eg. NAME and third eg. mirko, open printwriter and send msg
	public void messageClient(String[] sen) 
	{
		if(sen.length == 3) 
		{
			if(sen[1].equals("ID")) 
			{
				Client cli = findClient(sen[2], 0);
				if(cli != null) 
				{
					cli.cliThread.out.println("Imate novu poruku!");
				}
			}
			else if(sen[1].equals("NAME")) 
			{
				Client cli = findClient(sen[2], 1);
				if(cli != null) 
				{
					cli.cliThread.out.println("Imate novu poruku!");
				}
			}
			else 
			{
				srerror("Couldn't send message to that user");
			}
		}
		else 
		{
			srerror("Badly written KICK command");
		}
	}
	
	//Based on second param eg. NAME and third eg. mirko, close conn and flush everything
	public void kickClient(String[] sen)
	{
		if(sen.length == 3) 
		{
			if(sen[1].equals("ID")) 
			{
				Client cli = findClient(sen[2], 0);
				if(cli != null) 
				{
					cli.cliThread.removeClient();
				}
			}
			else if(sen[1].equals("NAME")) 
			{
				Client cli = findClient(sen[2], 1);
				if(cli != null) 
				{
					cli.cliThread.removeClient();;
				}
			}
			else 
			{
				srerror("Couldn't send message to that user");
			}
		}
		else 
		{
			srerror("Badly written KICK command");
		}
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
	public String srlist(String[] param) 
	{
		if(param.length < 2) 
		{
			srerror("Usage: LIST <parameter:NAME|CON|INET|DIR|PWD|PRIVS|ALL>");
			return "Usage: LIST <parameter:NAME|CON|INET|DIR|PWD|PRIVS|ALL>";
		}
		else 
		{
			String list = "LIST command: ";
			for(int i=0; i<256; i++) 
			{
				//System.out.println(clients[i]);
				if(clients[i] == null) 
				{
					continue;
				}
				else 
				{
					switch(param[1]) 
					{
						case "NAME":
							list += "    " + clients[i].name + " " + clients[i].socketID;
							//srprint(i + ": " + clients[i].name);
							break;
						case "CON":
							list += "    " + clients[i].name + " " + clients[i].isConsole;
							break;
						case "INET":
							list += "    " + clients[i].name + " " + clients[i].inetv4;
							break;
						case "DIR":
							list += "    " + clients[i].mainFileDir;
							break;
						case "PWD":
							list += "    " + clients[i].name + " " + clients[i].pwd;
							break;
						case "PRIVS":
							list += "    " + clients[i].name + " a:" + clients[i].isAdministrator + " p:" + clients[i].isPremium;
							break;
						case "ALL":
							list += "    " + clients[i];
							break;
						default:
							list += "    " + clients[i].name;
							break;
					}
				}
			}
			return list;
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
						srprint(srlist(parsed));
						break;
					case "MSG":
						messageClient(parsed);
						break;
					case "KICK":
						kickClient(parsed);
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
